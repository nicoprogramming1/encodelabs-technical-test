package com.technical_test.encodelabs.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public abstract class ApiException extends RuntimeException {
   private final int statusCode;
   
   public ApiException(int statusCode, String message, String context) {
      super(message);
      this.statusCode = statusCode;
      
      log.error(message, context);
   }
}
