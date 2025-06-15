package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * necesito un mapper más para las responses DTO
 * tanto el individual como para casos listados
 * no queremos devolver un model como tal sino quizás personalizar la response
 * sus atributos o como mínimo separar responsabilidades
 */
@Mapper(componentModel = "spring")
public abstract class ProductResponseMapper {
   
   public ProductResponseDTO toResponse(Product product) {
      return new ProductResponseDTO(
              product.getId(),
              product.getName(),
              product.getDescription(),
              product.getPrice().getAmount(),
              product.getPrice().getCurrencyCode(),
              product.getQuantity(),
              product.isActive(),
              product.getUpdatedAt()
      );
   }
   
   public List<ProductResponseDTO> toResponseList(List<Product> products) {
      return products.stream().map(this::toResponse).toList();
   }
}
