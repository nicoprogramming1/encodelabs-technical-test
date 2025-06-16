package com.technical_test.encodelabs.model;

import com.technical_test.encodelabs.dto.Product.ProductRequestDTO;
import com.technical_test.encodelabs.exception.BadRequestException;
import com.technical_test.encodelabs.model.builder.ProductBuilder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Respecto a los timestramps de creación o update:
 * como en este caso no afectan las reglas de negocio, van a estar solo en laentity
 * y devueltos al front mediante un DTO de response.
 * Si fueran parte imprescindible de la lógica de negocio, como por ejemplo tener en cuenta
 * la fecha de caducidad o aplicar descuento en base a una fecha, sí deberían modelarse aquí !!
 * Usa dos patterns creacionales distintos y cada uno resuelve una necesidad:
 * - Un builder que construye el objeto recuperado desde DB (mapper mediante)
 * - Un factory method para registrar un nuevo producto o actualizarlo
 */
@Slf4j
@Getter
@ToString
public class Product {
   
   private UUID id;
   private String name;
   private String description; // opcional
   private Money price;
   private Integer quantity;
   private boolean isActive;
   private LocalDateTime updatedAt;
   
   private final String className = this.getClass().getName();
   
   private Product(
           UUID id,
           String name,
           String description,
           Money price,
           Integer quantity,
           boolean isActive,
           LocalDateTime updatedAt  // por reglas de negocio lo voy a incluir
   ) {
      
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
      this.updatedAt = updatedAt;
      
      log.info("New product successfully registered: {}, id: {}", name, id);
   }
   
   // no voy a validar porque vienen de db (aunque validaciones mínimas no estarían mal ...)
   private Product(ProductBuilder builder) {
      this.id = builder.getId();
      this.name = builder.getName();
      this.description = builder.getDescription();
      this.price = builder.getPrice();
      this.quantity = builder.getQuantity();
      this.isActive = builder.isActive();
      this.updatedAt = builder.getUpdatedAt();
      
      log.info("Product successfully created by builder:\n{}", this); // deberia devolverme la clase
   }
   
   /**
    * Este factory method abstrae del constructor privado y delega a este la responsabilidad
    * de validar y devolvernos una instancia válida de un nuevo registro de producto
    *
    * @param dto de registro de un nuevo producto
    * @return el Product creado
    */
   public static Product create(ProductRequestDTO dto) {
      System.out.println("CREATE: " + dto.priceAmount());
      
      UUID id = UUID.randomUUID();
      Money price = new Money(dto.priceAmount());
      
      Validate.notNull(dto.priceAmount(), "Price amount cannot be null");
      Validate.isTrue(dto.priceAmount().compareTo(BigDecimal.ZERO) >= 0, "Price amount must be zero or positive");
      
      log.info("New product info setted in Product create method: {}, id: {}", dto.name(), id);
      
      return new Product(id, dto.name(), dto.description(), price, dto.quantity(), true, null);
   }
   
   public static ProductBuilder builder() {
      return new ProductBuilder();
   }
   
   public static Product fromBuilder(ProductBuilder builder) {
      return new Product(builder);
   }
   
   // TODO refactor: en vez de solo deactivate() deberia manejar tmb activate
   
   /**
    * Voy a manejar el cambio de estado del producto aquí, efectuando una u otra
    * operación según el boolean que llega por parámetro, reduciendo código, apelando
    * a DRY y a buenas prácticas
    *
    * @param status true para activar
    * @return el Product actualizado o la excepción
    */
   public Product enableOrDisable(boolean status) {
      if (this.isActive == status) {
         throw new BadRequestException(
                 status ? "The product is already active" : "The product is already disabled",
                 className
         );
      }
      
      return ProductBuilder.forRetrievedProduct(
              this.id,
              this.name,
              this.description,
              this.price,
              this.quantity,
              status,
              this.updatedAt
      );
   }
   
   public Product adjustStock(int stock) {
      int newStock = this.quantity + stock;
      
      if (newStock < 0) {
         throw new BadRequestException("Stock cant be negative", className);
      }
      
      return ProductBuilder.forRetrievedProduct(
              this.id,
              this.name,
              this.description,
              this.price,
              newStock,
              this.isActive,
              this.updatedAt
      );
   }
}
