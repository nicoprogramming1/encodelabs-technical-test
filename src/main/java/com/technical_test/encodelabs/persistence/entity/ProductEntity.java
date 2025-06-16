package com.technical_test.encodelabs.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Esta es la representación de la entidad en DB y desde donde
 * se genera la misma (code-first), cuenta con timestamps que se
 * auto asignan en la creación / actualización y asume los atributos
 * del VO Money como campos propios de Product
 */
@Entity
@Table(name = "products")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
   
   @Id
   private UUID id;
   
   @Column(nullable = false, length = 100)
   private String name;
   
   @Column(length = 300)
   private String description;
   
   @Column(nullable = false, precision = 10, scale = 2)
   private BigDecimal priceAmount;
   
   @Column(nullable = false)
   private String currencyCode; // decidí persistirlo
   
   @Column(nullable = false)
   private Integer quantity;
   
   @Column(nullable = false)
   private boolean isActive;  // por defecto es true
   
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;
   
   @Column(nullable = false)
   private LocalDateTime updatedAt;
   
   // auto generemos timestamps en operaciones save or update
   @PrePersist
   public void onCreate() {
      this.isActive = true;
      this.createdAt = this.updatedAt = LocalDateTime.now();
   }
   
   @PreUpdate
   public void onUpdate() {
      this.updatedAt = LocalDateTime.now();
   }
   
}
