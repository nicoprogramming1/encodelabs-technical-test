package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.dto.PaginatedResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.model.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
   
   public PaginatedResponseDTO<ProductResponseDTO> toPaginatedResponse(Page<Product> page) {
      List<ProductResponseDTO> dtos = toResponseList(page.getContent());
      return new PaginatedResponseDTO<>(
              dtos,
              page.getNumber(),
              page.getSize(),
              page.getTotalElements(),
              page.getTotalPages(),
              page.isLast()
      );
   }
}
