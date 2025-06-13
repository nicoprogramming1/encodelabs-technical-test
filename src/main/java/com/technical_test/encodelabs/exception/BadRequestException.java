package com.technical_test.encodelabs.exception;

public class BadRequestException extends ApiException {
   
   public BadRequestException(String message) {
      super(400, message);
   }
   
}
