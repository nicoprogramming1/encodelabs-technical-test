package com.technical_test.encodelabs.persistence.mapper;

import com.technical_test.encodelabs.dto.Product.ProductRegisterRequestDTO;
import com.technical_test.encodelabs.model.Money;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.model.builder.ProductBuilder;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

/**
 * Se encarga de mapear a ProductEntity para guardar el producto en db
 * Y a domain al recuperar un producto registrado.
 * Aquí tuve muchos problemas porque MapStruct necesita el constructor público
 * o setters y me preocupaba romper la encapsulación e inmutabilidad de Product
 * Lo resolví mediante un builder custom (ProductBuilder) el cual asume
 * la responsabildiad de crear u producto recuperado de forma segura
 * sin tener que violar el acceso restringido que debe tener Product.
 */
@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public abstract class ProductMapper {
   
   @Mapping(target = "priceAmount", source = "price", qualifiedByName = "mapToAmount")
   @Mapping(target = "currencyCode", source = "price", qualifiedByName = "mapToCurrencyCode")
   @Mapping(target = "isActive", source = "active")
   public abstract ProductEntity toEntity(Product product);
   
   public Product toDomain(ProductEntity entity) {
      Money price = new Money(entity.getPriceAmount());
      return ProductBuilder.forRetrievedProduct(
              entity.getId(),
              entity.getName(),
              entity.getDescription(),
              price,
              entity.getQuantity(),
              entity.isActive(),
              entity.getUpdatedAt()
      );
   }
   
   /**
    * Mapper para update de un producto, tomando desde el dto que llega
    * los datos a actualizar. El campo updatedAt se actualiza de manera automática (@PostPersist)
    * El isActive sólo puede actualizarse por su propio endpoint
    * @param productFromDto que viene de la request
    * @param retrievedProduct como resultado del mapping
    * @return un Product con info actualizada
    */
   public Product updateFromDTO(ProductRegisterRequestDTO productFromDto, Product retrievedProduct) {
      BigDecimal priceAmount = new BigDecimal(String.valueOf(productFromDto.priceAmount()));
      Money price = new Money(priceAmount);
      System.out.println("SAVE received product: " + retrievedProduct);
      
      return ProductBuilder.forRetrievedProduct(
              retrievedProduct.getId(),
              productFromDto.name(),
              productFromDto.description(),
              price,
              productFromDto.quantity(),
              retrievedProduct.isActive(),
              retrievedProduct.getUpdatedAt()
      );
   }
}
