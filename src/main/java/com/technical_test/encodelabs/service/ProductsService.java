package com.technical_test.encodelabs.service;

import com.technical_test.encodelabs.common.util.LogInfo;
import com.technical_test.encodelabs.dto.PaginatedResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductRegisterRequestDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.exception.ResourceNotFoundException;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.entity.ProductEntity;
import com.technical_test.encodelabs.persistence.mapper.ProductMapper;
import com.technical_test.encodelabs.persistence.mapper.ProductResponseMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductsService {
   private final ProductRepository productRepository;
   private final ProductResponseMapper responseMapper;
   private final ProductMapper domainMapper;
   private final MessageService msgService;
   private final LogInfo log;
   private final String className = this.getClass().getName(); // para el context de logs
   
   public ProductsService(
           ProductRepository productRepository,
           ProductResponseMapper responseMapper,
           LogInfo log,
           ProductMapper domainMapper,
           MessageService msgService
   ) {
      this.productRepository = productRepository;
      this.responseMapper = responseMapper;
      this.log = log;
      this.domainMapper = domainMapper;
      this.msgService = msgService;
   }
   
   public ProductResponseDTO create(ProductRegisterRequestDTO requestDTO) {
      Product newProduct = Product.create(requestDTO);
      Product savedPoduct = productRepository.save(newProduct);
      
      log.logInfoAction("product.saved", savedPoduct, className);
      return responseMapper.toResponse(savedPoduct);
   }
   
   public PaginatedResponseDTO<ProductResponseDTO> retrieveAll(Pageable pageable) {
      Page<Product> page = productRepository.findAll(pageable);
      PaginatedResponseDTO<ProductResponseDTO> response = responseMapper.toPaginatedResponse(page);
      
      log.logInfoAction("product.list", page, className);
      return response;
   }
   
   public ProductResponseDTO retrieveOne(UUID id) {
      ProductEntity productEntity = productRepository.findById(id).orElseThrow(() ->
              new ResourceNotFoundException(msgService.get("product.notFound"), className));
      Product product = domainMapper.toDomain(productEntity);
      
      log.logInfoAction("product.retrieved", product, className);
      return responseMapper.toResponse(product);
   }
   
}
