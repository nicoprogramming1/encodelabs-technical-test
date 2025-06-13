package com.technical_test.encodelabs.exception;

public final class ResourceNotFoundException extends ApiException {
   
   public ResourceNotFoundException(String message) {
      super(404, message);
   }
}
