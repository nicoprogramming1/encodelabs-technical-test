package com.technical_test.encodelabs.model;

import com.technical_test.encodelabs.model.builder.ProductBuilder;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Respecto a los timestramps de creación o update:
 * como en este caso no afectan las reglas de negocio, van a estar solo en laentity
 * y devueltos al front mediante un DTO de response.
 * Si fueran parte imprescindible de la lógica de negocio, como por ejemplo tener en cuenta
 * la fecha de caducidad o aplicar descuento en base a una fecha, sí deberían modelarse aquí !!
 */
@Getter
public class Product {
   
   private UUID id;
   private String name;
   private String description; // opcional
   private Money price;
   private Integer quantity;
   private boolean isActive;
   
   
   private Product(UUID id, String name, String description, Money price, Integer quantity, boolean isActive) {
      
      Validate.notNull(id, "Id cannot be null");
      Validate.isInstanceOf(UUID.class, id, "Id must be an UUID instance");
      
      Validate.notNull(name, "Name cannot be null");
      Validate.notBlank(name, "Name cannot be blank");
      Validate.isTrue(name.length() >= 3 && name.length() <= 100, "Name must be between 3 and 100 characters");
      
      if (description != null) {
         Validate.isTrue(description.length() <= 300, "Description must be at most 300 characters");
      }
      
      Validate.notNull(quantity, "Quantity cannot be null");
      Validate.isTrue(quantity >= 0, "Quantity must be zero or positive");
      
      this.id = id;
      this.name = name;
      this.description = description;
      this.price = price;
      this.quantity = quantity;
      this.isActive = isActive;  // true pór defecto
   }
   
   private Product(ProductBuilder builder) {
      this.id = builder.getId();
      this.name = builder.getName();
      this.description = builder.getDescription();
      this.price = builder.getPrice();
      this.quantity = builder.getQuantity();
      this.isActive = builder.isActive();
   }
   
   /**
    * Este factory method abstrae del constructor privado y delega a este la responsabilidad
    * de validar y devolvernos una instancia válida
    * En una entidad muy grande quizás usaría un builder y lo separaría en su clase propia
    *
    * @param name        de nuevo producto
    * @param description puede se opcional
    * @param priceAmount de tipo BigDecimal para precisión
    * @param quantity    puede ser 0 o positivo
    * @return el Product creado
    */
   public static Product create(String name, String description, BigDecimal priceAmount, Integer quantity) {
      UUID id = UUID.randomUUID();
      Money price = new Money(priceAmount);
      
      Validate.notNull(priceAmount, "Price amount cannot be null");
      Validate.isTrue(priceAmount.compareTo(BigDecimal.ZERO) >= 0, "Price amount must be zero or positive");
      
      return new Product(id, name, description, price, quantity, true);
   }
   
   public static ProductBuilder builder() {
      return new ProductBuilder();
   }
   
   public static Product fromBuilder(ProductBuilder builder) {
      return new Product(builder);
   }
   
   // MÉTODOS PROPIOS QUE NO SE VAN A IMPLEMENTAR por ej AddProduct para agregar stock a un prod registrado, etc
}
