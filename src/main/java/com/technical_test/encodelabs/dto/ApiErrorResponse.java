package com.technical_test.encodelabs.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ApiErrorResponse {
   private LocalDateTime timestamp = LocalDateTime.now();
   private final int statusCode;
   private final String message;
   private final String path;
   
   public ApiErrorResponse(int statusCode, String message, String path) {
      this.statusCode = statusCode;
      this.message = message;
      this.path = path;
   }
   
}
