package com.technical_test.encodelabs.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Este service me da acceso a los messages.properties de resources
 * Usa la interfaz MessageSource para esto y mediante el get
 * accedemos al mensaje que necesitemos, incluso gracias al Locale.getDefault
 * sabe el idioma de la JVM. Solo configuré idioma EN y ES.
 * Esto podría mejorarse leyendo de la request HTTP el idioma enviado
 * con LocaleResolver y el header Accept-Language
 */
@Service
public class MessageService {
   
   private final MessageSource messageSource;
   
   public MessageService(MessageSource messageSource) {
      this.messageSource = messageSource;
   }
   
   public String get(String code) {
      return messageSource.getMessage(code, null, Locale.getDefault());
   }
}
