package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.model.Money;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.model.builder.ProductBuilder;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public abstract class ProductMapper {
   
   @Mapping(target = "priceAmount", source = "price", qualifiedByName = "mapToAmount")
   @Mapping(target = "currencyCode", source = "price", qualifiedByName = "mapToCurrencyCode")
   public abstract ProductEntity toEntity(Product product);
   
   public Product toDomain(ProductEntity entity) {
      Money price = new Money(entity.getPriceAmount());
      return ProductBuilder.forRetrievedProduct(
              entity.getId(),
              entity.getName(),
              entity.getDescription(),
              price,
              entity.getQuantity(),
              entity.isActive()
      );
   }
   
}
