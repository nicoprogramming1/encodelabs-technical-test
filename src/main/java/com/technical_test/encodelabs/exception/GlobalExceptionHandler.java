package com.technical_test.encodelabs.exception;

import com.technical_test.encodelabs.common.util.ApiErrorLogger;
import com.technical_test.encodelabs.dto.ApiResponseDTO;
import com.technical_test.encodelabs.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase se va a encargar de capturar los errores REST
 * y devolver una respuesta apropiada con ResponseEntity<T> según el caso
 * Discriminaré las excepciones por validaciones, las ApiException que yo
 * configure, y las generales en último lugar
 */
@Slf4j
@RestControllerAdvice
public final class GlobalExceptionHandler {
   
   private final MessageService msgService;
   
   @Autowired
   public GlobalExceptionHandler(MessageService msgService) {
      this.msgService = msgService;
   }
   
   
   /**
    * Captura los errores de tipo ApiException
    * Para el front devuelvo info no sensible, pero en el logger interno discrimino
    * el path y el timestamp para debuggin y para control interno
    *
    * @param apiException capturado al lanzarse una excepción de este tipo
    * @param req          me da acceso al contexto de la HTTP request, tomo el path del error
    * @return un ResponseEntity de tipo ApiResponse para dar una respuesto standard
    */
   @ExceptionHandler(ApiException.class)
   public ResponseEntity<ApiResponseDTO<List<Object>>> apiExceptionHandler(
           ApiException apiException,
           HttpServletRequest req
   ) {
      // logueamos el error para uso interno
      ApiErrorLogger.errorLog(
              apiException.getMessage(),
              apiException.getStatusCode(),
              req.getRequestURI()
      );
      
      ApiResponseDTO<List<Object>> error = ApiResponseDTO.failure(apiException.getMessage());
      // builder de ResponseEntity
      return ResponseEntity.status(apiException.getStatusCode()).body(error);
   }
   
   // captura los erorres de validate
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponseDTO<?>> validationHandler(
           MethodArgumentNotValidException exception,
           HttpServletRequest req
   ) {
      
      String errors = exception.getBindingResult()
              .getFieldErrors()
              .stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.joining("; "));
      
      ApiErrorLogger.errorLog(
              errors,
              400,
              req.getRequestURI()
      );
      
      ApiResponseDTO<?> error = ApiResponseDTO.failure(errors);
      
      return ResponseEntity.status(400).body(error);
   }
   
   // captura todos los errores generales de la aplicación
   @ExceptionHandler(Exception.class)
   public ResponseEntity<ApiResponseDTO<List<Object>>> genericHandler(
           Exception exception,
           HttpServletRequest req
   ) {
      log.error("Excepción genérica atrapada:", exception);
      ApiErrorLogger.errorLog(
              msgService.get("error.server"),
              500,
              req.getRequestURI()
      );
      
      ApiResponseDTO<List<Object>> error = ApiResponseDTO.failure(msgService.get("error.server"));
      
      return ResponseEntity.status(500).body(error);
   }
   
}
