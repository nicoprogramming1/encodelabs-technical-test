package com.technical_test.encodelabs.model.builder;

import com.technical_test.encodelabs.model.Money;
import com.technical_test.encodelabs.model.Product;
import lombok.Getter;

import java.util.UUID;

@Getter

public final class ProductBuilder {
   
   private UUID id;
   private String name;
   private String description;
   private Money price;
   private Integer quantity;
   private boolean isActive;
   
   public static Product forRetrievedProduct(
           UUID id,
           String name,
           String description,
           Money price,
           Integer quantity,
           boolean isActive
   ) {
      return Product.builder()
              .withId(id)
              .withName(name)
              .withDescription(description)
              .withPrice(price)
              .withQuantity(quantity)
              .withIsActive(isActive)
              .build();
   }
   
   public Product build() {
      return Product.fromBuilder(this);
   }
   
   public ProductBuilder withId(UUID id) {
      this.id = id;
      return this;
   }
   
   public ProductBuilder withName(String name) {
      this.name = name;
      return this;
   }
   
   public ProductBuilder withDescription(String description) {
      this.description = description;
      return this;
   }
   
   public ProductBuilder withPrice(Money price) {
      this.price = price;
      return this;
   }
   
   public ProductBuilder withQuantity(Integer quantity) {
      this.quantity = quantity;
      return this;
   }
   
   public ProductBuilder withIsActive(boolean isActive) {
      this.isActive = isActive;
      return this;
   }
}
