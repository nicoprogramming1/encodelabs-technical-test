package com.technical_test.encodelabs.exception;

public final class ResourceNotFoundException extends ApiException {
   
   public ResourceNotFoundException(String message, String context) {
      super(404, message, context);
   }
}
