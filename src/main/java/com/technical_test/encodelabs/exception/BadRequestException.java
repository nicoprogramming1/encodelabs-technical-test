package com.technical_test.encodelabs.exception;

public final class BadRequestException extends ApiException {
   
   public BadRequestException(String message, String context) {
      super(400, message, context);
   }
   
}
