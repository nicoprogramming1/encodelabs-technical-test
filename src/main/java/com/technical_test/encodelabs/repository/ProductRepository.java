package com.technical_test.encodelabs.repository;

import com.technical_test.encodelabs.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
   Product save(Product product);
   void saveAll(List<Product> products);  // para seeder pero podría usarse para inserts múltiples
   Page<Product> findAll(Pageable pageable);
   List<Product> findActiveAll();
   List<Product> findInactiveAll();
   Optional<Product> findById(UUID id);
   UUID deleteById(UUID id);
   boolean existsById(UUID id);
   boolean existsByName(String name);
   boolean isActive(UUID id);
   Long count();
}
