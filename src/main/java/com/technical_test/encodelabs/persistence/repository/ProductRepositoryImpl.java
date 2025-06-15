package com.technical_test.encodelabs.persistence.repository;

import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import com.technical_test.encodelabs.persistence.mapper.ProductMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import com.technical_test.encodelabs.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta clase es la implementación (patrón repository) del product repository
 * es aquí donde actúa el mapper trasnformando a domain o entity según el caso
 */
@Slf4j
@Repository
public class ProductRepositoryImpl implements ProductRepository {
   
   private final ProductJPARepository jpaRepository;
   private final ProductMapper mapper;
   private final MessageService msgService;
   
   public ProductRepositoryImpl(
           ProductJPARepository jpaRepository,
           ProductMapper mapper,
           MessageService msgService
   ) {
      this.jpaRepository = jpaRepository;
      this.mapper = mapper;
      this.msgService = msgService;
   }
   
   @Override
   public Product save(Product product) {
      ProductEntity entityToSave = mapper.toEntity(product);
      ProductEntity savedProduct = jpaRepository.save(entityToSave);
      
      log.info("{}{}", msgService.get("product.saved"), savedProduct);
      return mapper.toDomain(savedProduct);
   }
   
   @Override
   public void saveAll(List<Product> products) {
      List<ProductEntity> productEntities = products.stream().map(mapper::toEntity).toList();
      jpaRepository.saveAll(productEntities);
      log.info("{}{}", msgService.get("product.allSaved"), productEntities);
   }
   
   @Override
   public List<Product> findAll() {
      List<Product> products = jpaRepository.findAll().stream().map(mapper::toDomain).toList();
      log.info("{}{}", msgService.get("product.list"), products);
      return products;
   }
   
   @Override
   public List<Product> findActiveAll() {
      List<Product> products = jpaRepository.findActiveAll().stream().map(mapper::toDomain).toList();
      log.info("{}{}", msgService.get("product.activeList"), products);
      return products;
   }
   
   @Override
   public List<Product> findInactiveAll() {
      List<Product> products = jpaRepository.findInactiveAll().stream().map(mapper::toDomain).toList();
      log.info("{}{}", msgService.get("product.inactiveList"), products);
      return products;
   }
   
   @Override
   public Optional<Product> findById(UUID id) {
      Optional<Product> product = jpaRepository.findById(id).map(mapper::toDomain);
      if(product.isPresent()) {
         log.info("{}{}", msgService.get("product.retrieved"), product);
      } else {
         log.warn("{}{}", msgService.get("product.notFound"), product);
      }
      return product;
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
