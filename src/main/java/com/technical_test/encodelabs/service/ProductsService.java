package com.technical_test.encodelabs.service;

import com.technical_test.encodelabs.common.util.LogInfo;
import com.technical_test.encodelabs.dto.PaginatedResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductRegisterRequestDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductUpdateRequestDTO;
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
      Product savedProduct = productRepository.save(newProduct);
      
      log.logInfoAction("product.saved", savedProduct, className);
      return responseMapper.toResponse(savedProduct);
   }
   
   public PaginatedResponseDTO<ProductResponseDTO> retrieveAll(Pageable pageable) {
      Page<Product> page = productRepository.findAll(pageable);
      PaginatedResponseDTO<ProductResponseDTO> response = responseMapper.toPaginatedResponse(page);
      
      log.logInfoAction("product.list", page, className);
      return response;
   }
   
   public ProductResponseDTO retrieveOne(UUID id) {
      Product existingProduct = productRepository.findById(id).orElseThrow(() ->
              new ResourceNotFoundException(msgService.get("product.notFound"), className));
      
      log.logInfoAction("product.retrieved", existingProduct, className);
      return responseMapper.toResponse(existingProduct);
   }
   
   /**
    * Para el update, usamos un save en realidad, el mismo del registro
    * ya que vamos a implementar un PUT (no PATCH) por motivos explicados en el Readme.md
    * La actualización como tal ocurre en memoria mediante un mapperm no utiliza el builder o el create
    * de Product porque de lo contrario pisaría los createdAt o updatedAt, el isActive, etc
    * De esta manera el save puede ejecutar su @PostPersist ignorando el @PrePersist
    * Quizás en un enfoque aún más basado en DDD, debería Product tener un update propio
    * y este llame al mapper y realice validaciones, aunque también habría que lidiar
    * con el hecho de que Product es inmutable, por lo que devolveríamos una nueva instancia en realidad
    * Lo voy a dejar así como está, no me siento incómodo al respecto tampoco!.
    * @param requestBody desde el front
    * @return un ProductResponseDTO
    */
   public ProductResponseDTO updateOne(UUID id, ProductRegisterRequestDTO requestBody) {
      Product existingProduct = productRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(msgService.get("product.notFound"), className));
      
      // actualizamos en memoria
      Product productToUpdate = domainMapper.updateFromDTO(requestBody, existingProduct);
      System.out.println("SAVE received product: " + productToUpdate);
      Product saved = productRepository.save(productToUpdate);
      System.out.println("SAVE product: " + saved);
      
      log.logInfoAction("product.updated", saved, className);
      return responseMapper.toResponse(saved);
   }
   
}
