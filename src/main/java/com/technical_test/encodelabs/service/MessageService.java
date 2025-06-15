package com.technical_test.encodelabs.service;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Este service me da acceso a los messages.properties de resources
 * Usa la interfaz MessageSource para esto y mediante el get
 * accedemos al mensaje que necesitemos, incluso gracias al Locale.getDefault
 * sabe el idioma de la JVM.
 * ACTUALIZACIÓN 15/6
 * Estoy renegando demasiado con 3 lenguajes, EN, ES Y ES_AR
 * no consigo que funcionen los 3 y hay muchos conflictos y estoy demorando
 * y no creo que sea tan importante, voy a volver atrás y dejar sólo EN
 * y eliminar toda la config que había adaptado para 3 lenguajes.
 * Ya lo practicaré por mi cuenta con tiempo :)
 */
@Service
public class MessageService {
   
   private final MessageSource messageSource;
   
   public MessageService(MessageSource messageSource) {
      this.messageSource = messageSource;
   }
   
public String get(String code) {
   try {
      return messageSource.getMessage(code, null, Locale.ENGLISH);
   } catch (NoSuchMessageException e) {
      System.out.println("DEBUG - No se encontró!!: " + code);
      return "Missing message: " + code;
   }
}
}
