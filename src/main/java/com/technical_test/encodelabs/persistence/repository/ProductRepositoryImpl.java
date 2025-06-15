package com.technical_test.encodelabs.persistence.repository;

import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import com.technical_test.encodelabs.persistence.mapper.ProductMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta clase es la implementación (patrón repository) del product repository
 * es aquí donde actúa el mapper trasnformando a domain o entity según el caso
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
   
   private final ProductJPARepository jpaRepository;
   private final ProductMapper mapper;
   
   public ProductRepositoryImpl(ProductJPARepository jpaRepository, ProductMapper mapper) {
      this.jpaRepository = jpaRepository;
      this.mapper = mapper;
   }
   
   @Override
   public Product save(Product product) {
      ProductEntity entityToSave = mapper.toEntity(product);
      ProductEntity savedProduct = jpaRepository.save(entityToSave);
      return mapper.toDomain(savedProduct);
   }
   
   @Override
   public void saveAll(List<Product> products) {
      List<ProductEntity> productEntities = products.stream().map(mapper::toEntity).toList();
      jpaRepository.saveAll(productEntities);
   }
   
   @Override
   public List<Product> findAll() {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
   }
   
   @Override
   public List<Product> findActiveAll() {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
   }
   
   @Override
   public List<Product> findInactiveAll() {
      return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
   }
   
   @Override
   public Optional<Product> findById(UUID id) {
      return jpaRepository.findById(id).map(mapper::toDomain);
   }
   
   @Override
   public UUID deleteById(UUID id) {
      jpaRepository.deleteById(id);
      return id;
   }
   
   @Override
   public UUID deactivateById(UUID id) {
      return id;
   }
   
   @Override
   public UUID reactivateById(UUID id) {
      return id;
   }
   
   @Override
   public boolean existsById(UUID id) {
      return jpaRepository.existsById(id);
   }
   
   @Override
   public boolean isActive(UUID id) {
      return jpaRepository.isActive(id);
   }
   
   // cuenta los productos registrados
   @Override
   public Long count() {
      return jpaRepository.count();
   }
}
