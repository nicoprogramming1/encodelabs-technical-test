package com.technical_test.encodelabs.service;

import com.technical_test.encodelabs.common.util.LogInfo;
import com.technical_test.encodelabs.dto.PaginatedResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductRegisterRequestDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.model.Product;
import com.technical_test.encodelabs.persistence.mapper.ProductResponseMapper;
import com.technical_test.encodelabs.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductsService {
   private final ProductRepository productRepository;
   private final ProductResponseMapper mapper;
   private final LogInfo log;
   private final String className = this.getClass().getName();
   
   
   public ProductsService(
           ProductRepository productRepository,
           ProductResponseMapper mapper,
           LogInfo log
   ) {
      this.productRepository = productRepository;
      this.mapper = mapper;
      this.log = log;
   }
   
   
   public ProductResponseDTO create(ProductRegisterRequestDTO requestDTO) {
      Product newProduct = Product.create(requestDTO);
      Product savedPoduct = productRepository.save(newProduct);
      
      log.logInfoAction("product.saved", savedPoduct, className);
      return mapper.toResponse(savedPoduct);
   }
   
   public PaginatedResponseDTO<ProductResponseDTO> retrieveAll(Pageable pageable) {
      Page<Product> page = productRepository.findAll(pageable);
      PaginatedResponseDTO<ProductResponseDTO> response = mapper.toPaginatedResponse(page);
      
      log.logInfoAction("product.list", page, className);
      return response;
   }
}
