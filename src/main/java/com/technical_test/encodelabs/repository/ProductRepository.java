package com.technical_test.encodelabs.repository;

import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta interfaz define un repositorio de dominio y declara el
 * comportamiento que debe tener un producto, np se rela ciona
 * de forma directa con JPA, reduce acoplamiento
 */
public interface ProductRepository {
   
   ProductEntity save(Product product);   // crea o actualiza
   
   void saveAll(List<Product> products); // para seeder (podría usarse para crear múltiples products)
   
   Page<ProductEntity> findAll(Pageable pageable);
   
   List<ProductEntity> findActiveAll();
   
   List<ProductEntity> findInactiveAll();
   
   Optional<ProductEntity> findById(UUID id);
   
   UUID deleteById(UUID id);
   
   UUID deactivateById(UUID id);
   
   UUID reactivateById(UUID id);
   
   boolean existsById(UUID id);
   
   boolean isActive(UUID id);
   
   Long count();
}
