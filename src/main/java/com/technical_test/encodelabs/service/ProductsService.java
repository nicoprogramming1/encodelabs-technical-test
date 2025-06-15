package com.technical_test.encodelabs.service;

import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.mapper.ProductResponseMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductsService {
   private final ProductRepository productRepository;
   private final ProductResponseMapper mapper;
   private final MessageService msgService;
   
   public ProductsService(
           ProductRepository productRepository,
           ProductResponseMapper mapper,
           MessageService msgService
   ) {
      this.productRepository = productRepository;
      this.mapper = mapper;
      this.msgService = msgService;
   }
   
   public List<ProductResponseDTO> retrieveAll() {
      List<Product> products = productRepository.findAll();
      List<ProductResponseDTO> productResponseDTOS = mapper.toResponseList(products);
      
      log.info("{}: {}", msgService.get("product.list"), products);
      return productResponseDTOS;
   }
}
