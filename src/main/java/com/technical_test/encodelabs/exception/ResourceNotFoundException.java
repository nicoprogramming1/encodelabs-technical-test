package com.technical_test.encodelabs.exception;

public class ResourceNotFoundException extends ApiException {
   
   public ResourceNotFoundException(String message) {
      super(404, message);
   }
}
