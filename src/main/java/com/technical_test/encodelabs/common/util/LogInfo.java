package com.technical_test.encodelabs.common.util;

import com.technical_test.encodelabs.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

// Logger de info reutilizable
@Slf4j
@Component
public class LogInfo {
   private final MessageService msgService;
   
   public LogInfo(MessageService msgService){
      this.msgService = msgService;
   }
   
   /**
    * Puede ser llamado desde cualquier lugar y registrar todoel contexto
    * @param logKey el código del message.properties
    * @param data involucrada en la operación que desee ser logueada
    * @param className para registrar el contexto donde sucedió
    */
   public void logInfoAction(String logKey, Object data, String className) {
      String key = msgService.get(logKey);
      log.info("{}: {}, context: {}", key, data, className);
   }
   
}
