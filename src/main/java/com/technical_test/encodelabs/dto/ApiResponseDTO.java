package com.technical_test.encodelabs.dto;

import java.util.List;

/**
 * Es un record para mantener la inmutabilidad, además genera automáticamente el
 * boiler plate, siendo muy liviana pero con la seguridad de una clase
 * @param success se asigna automáticamente dependiendo si se llama success / failure
 * @param message de la operación
 * @param data con la información a presentar como un array, en caso de no devolver info se asigna []
 * @param <T> genérico, será el tipo de la data (List<ProductResponseDTO> por ej)
 *
 *           Quizás debería haber hecho que el List<> de la data se genere acá y no tener
 *           que hacer List.of por todas partes antes de enviarlo aquí, estoy repitiendo codigo
 */
public record ApiResponseDTO<T>(
        boolean success,
        String message,
        T data
) {
   
   public static <T> ApiResponseDTO<List<T>> success(String message, List<T> data) {
      return new ApiResponseDTO<>(true, message, data != null ? data : List.of());
   }
   
   public static ApiResponseDTO<List<Object>> failure(String message) {
      return new ApiResponseDTO<>(false, message, List.of());
   }
   
   public static <T> ApiResponseDTO<PaginatedResponseDTO<T>> paginatedSuccess(String message, PaginatedResponseDTO<T> data) {
      return new ApiResponseDTO<>(true, message, data);
   }
}
