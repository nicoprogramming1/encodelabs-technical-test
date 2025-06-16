package com.technical_test.encodelabs.persistence.repository;

import com.technical_test.encodelabs.common.util.LogInfo;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import com.technical_test.encodelabs.persistence.mapper.ProductMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   private final LogInfo log;
   private final String className = this.getClass().getName();
   
   public ProductRepositoryImpl(
           ProductJPARepository jpaRepository,
           ProductMapper mapper,
           LogInfo log
   ) {
      this.jpaRepository = jpaRepository;
      this.mapper = mapper;
      this.log = log;
   }
   
   @Override
   public Product save(Product product) {
      ProductEntity entityToSave = mapper.toEntity(product);
      ProductEntity saved = jpaRepository.save(entityToSave);
      return mapper.toDomain(saved);
   }
   
   @Override
   public void saveAll(List<Product> products) {
      List<ProductEntity> entities = products.stream().map(mapper::toEntity).toList();
      jpaRepository.saveAll(entities);
      log.logInfoAction("product.allSaved", entities, className);
   }
   
   @Override
   public Page<Product> findAll(Pageable pageable) {
      return jpaRepository.findAll(pageable).map(mapper::toDomain);
   }
   
   @Override
   public List<Product> findActiveAll() {
      return jpaRepository.findActiveAll().stream().map(mapper::toDomain).toList();
   }
   
   @Override
   public List<Product> findInactiveAll() {
      return jpaRepository.findInactiveAll().stream().map(mapper::toDomain).toList();
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
      return id; // lógica pendiente
   }
   
   @Override
   public UUID reactivateById(UUID id) {
      return id; // lógica pendiente
   }
   
   @Override
   public boolean existsById(UUID id) {
      return jpaRepository.existsById(id);
   }
   
   @Override
   public boolean isActive(UUID id) {
      return jpaRepository.isActive(id);
   }
   
   @Override
   public Long count() {
      return jpaRepository.count();
   }
}
