package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public interface ProductMapper {
   
   @Mapping(target = "price", source = ".", qualifiedByName = "mapToMoney")
   Product toDomain(ProductEntity productEntity);
   
   @Mapping(target = "amount", source = ".", qualifiedByName = "mapToAmount")
   @Mapping(target = "currencyCode", source = ".", qualifiedByName = "mapToCurrencyCode")
   ProductEntity toEntity(Product product);
}
