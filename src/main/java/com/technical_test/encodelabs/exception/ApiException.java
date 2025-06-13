package com.technical_test.encodelabs.exception;

public abstract class ApiException extends RuntimeException {
   
   private final int statusCode;
   
   public ApiException(int statusCode, String message) {
      super(message);
      this.statusCode = statusCode;
   }
   
   public int getStatusCode() {
      return this.statusCode;
   }
}
