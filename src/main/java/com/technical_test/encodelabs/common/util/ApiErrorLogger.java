package com.technical_test.encodelabs.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Esta clase va a ser reutilizada en los exception handlers
 * por motivos de cumplir con el principio DRY se separa
 */
@Slf4j
public final class ApiErrorLogger {
   
   public static void errorLog(String message, int statusCode, String path) {
      final LocalDateTime timestamp = LocalDateTime.now();
      log.error("""
              🔴 API Error
              ├─ Message: {}
              ├─ Code: {}
              ├─ Path: {}
              └─ Timestamp: {}
              """, message, statusCode, path, timestamp);
   }
}
