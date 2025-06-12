package com.technical_test.encodelabs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Se especifica la url de los repositorios
 * Se ponen a disposición los transactions y se ejecute rollback en caso de error
 */
@Slf4j
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.technical_test.encodelabs.repository")
@EnableTransactionManagement
public class EncodelabsApplication {
   
   public static void main(String[] args) {
      
      SpringApplication app = new SpringApplication(EncodelabsApplication.class);
      app.setAdditionalProfiles("dev");
      
      // ejecutamos el programa y lo guardamos en context para acceder a los env
      ApplicationContext context = app.run(args);
      
      String port = context.getEnvironment().getProperty("SERVER_PORT");
      String appName = context.getEnvironment().getProperty("APP_NAME");
      
      log.info("Iniciando aplicación: {}", appName);
      System.out.println("Running on port: " + port);
   }
   
}