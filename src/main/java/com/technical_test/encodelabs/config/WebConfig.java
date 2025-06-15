package com.technical_test.encodelabs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class WebConfig {
   
   /**
    * Cross-origin config
    * Para desarrollo permito el acceso a hacer una petición a cualquier origen
    * Las credentials deben ser false o de lo contrario deberíamos definir
    * los orígenes permitidos (url front)
    * Con respecto a los headers, también se permite el envío de todos,
    * en producción definiríamos los necesarios, ejemplo:
    * configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
    * @return el source con la config registrada como bean
    */
   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      
      CorsConfiguration configuration = new CorsConfiguration();
      
      configuration.setAllowedOriginPatterns(List.of("*"));
      configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
      configuration.setAllowedHeaders(List.of("*"));
      configuration.setAllowCredentials(false);
      
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/api/**", configuration);
      return source;
   }
}