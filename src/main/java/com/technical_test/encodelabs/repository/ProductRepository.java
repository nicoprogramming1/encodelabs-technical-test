package com.technical_test.encodelabs.repository;

import com.technical_test.encodelabs.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta interfaz define un repositorio de dominio y declara el
 * comportamiento que debe tener un producto, np se rela ciona
 * de forma directa con JPA, reduce acoplamiento
 */
public interface ProductRepository {
   
   Product save(Product product);   // crea o actualiza
   
   void saveAll(List<Product> products); // para seeder (podría usarse para crear múltiples products)
   
   List<Product> findAll();
   
   List<Product> findActiveAll();
   
   List<Product> findInactiveAll();
   
   Optional<Product> findById(UUID id);
   
   UUID deleteById(UUID id);
   
   UUID deactivateById(UUID id);
   
   UUID reactivateById(UUID id);
   
   boolean existsById(UUID id);
   
   boolean isActive(UUID id);
   
   Long count();
}
