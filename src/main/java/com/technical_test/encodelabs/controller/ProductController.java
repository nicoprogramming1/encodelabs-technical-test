package com.technical_test.encodelabs.controller;

import com.technical_test.encodelabs.dto.ApiResponseDTO;
import com.technical_test.encodelabs.dto.PaginatedResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductRequestDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductStatusDTO;
import com.technical_test.encodelabs.dto.Product.ProductStockDTO;
import com.technical_test.encodelabs.service.MessageService;
import com.technical_test.encodelabs.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product CRUD and operations")
public class ProductController {
   private final ProductsService productsService;
   private final MessageService msgService;
   
   public ProductController(ProductsService productsService, MessageService msgService) {
      this.productsService = productsService;
      this.msgService = msgService;
   }
   
   @GetMapping
   @Operation(summary = "List all registered products, both actives and inactives")
   @ApiResponse(responseCode = "200", description = "Listado paginado de productos")
   public ResponseEntity<ApiResponseDTO<PaginatedResponseDTO<ProductResponseDTO>>> products(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size
   ) {
      log.info("GET / called");
      
      Pageable pageable = PageRequest.of(page, size);
      PaginatedResponseDTO<ProductResponseDTO> products = productsService.retrieveAll(pageable);
      ApiResponseDTO<PaginatedResponseDTO<ProductResponseDTO>> response =
              ApiResponseDTO.paginatedSuccess(msgService.get("product.list"), products);
      
      return ResponseEntity.ok(response);
   }
   
   @Operation(summary = "Product register, active by default")
   @ApiResponse(responseCode = "201", description = "Product created")
   @PostMapping(path = "/register")
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> register(@RequestBody @Valid ProductRequestDTO requestDTO) {
      log.info("POST /register called");
      
      ProductResponseDTO productResponseDTO = productsService.create(requestDTO);
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.saved"), List.of(productResponseDTO));
      
      return ResponseEntity.status(201).body(response);
   }
   
   @Operation(summary = "Retrieve an active product by id")
   @ApiResponse(responseCode = "200", description = "Product retrieved")
   @GetMapping(path = "/{id}")
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getById(@PathVariable @NotNull UUID id) {
      log.info("GET /{id} called");
      
      ProductResponseDTO productResponseDTO = productsService.retrieveOne(id);
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.retrieved"), List.of(productResponseDTO));
      
      return ResponseEntity.ok(response);
   }
   
   @Operation(summary = "Update a product by id")
   @ApiResponse(responseCode = "201", description = "Product updated")
   @PutMapping(path = "/update/{id}")
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> update(
           @PathVariable @NotNull UUID id,
           @RequestBody @Valid ProductRequestDTO requestBody
   ) {
      log.info("PUT update/{id} called");
      
      ProductResponseDTO productResponseDTO = productsService.updateOne(id, requestBody);
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.saved"), List.of(productResponseDTO));
      return ResponseEntity.status(201).body(response);
   }
   
   // podria devolver el nombre quizás envez de id o el producto completo
   @Operation(summary = "Retrieve an active product by id")
   @ApiResponse(responseCode = "200", description = "Product retrieved")
   @DeleteMapping(path = "/{id}")
   public ResponseEntity<ApiResponseDTO<List<UUID>>> delete(@PathVariable @NotNull UUID id) {
      log.info("DELETE /{id} called");
      
      UUID idFromDeleted = productsService.deleteOne(id);   // se que es un poco redundante (ya tenia el id)
      ApiResponseDTO<List<UUID>> response =
              ApiResponseDTO.success(msgService.get("product.deleted"), List.of(idFromDeleted));
      return ResponseEntity.ok(response);
   }
   
   // podria devolver solo el id también........
   @Operation(summary = "Product change status, send in body false to deactivate")
   @ApiResponse(responseCode = "200", description = "Product status changed")
   @PatchMapping(path = "/status/{id}")
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> changeStatus(
           @PathVariable @NotNull UUID id,
           @RequestBody @Valid ProductStatusDTO status
   ) {
      log.info("PATCH /status/{id} called");
      
      ProductResponseDTO product = productsService.setStatus(id, status);   // false para deshabilitar
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.status"), List.of(product));
      return ResponseEntity.ok(response);
   }
   
   // dependeindo de si llega un stock positivo o negativo suma o resta
   @Operation(summary = "Add or remove stock for a product")
   @ApiResponse(responseCode = "200", description = "Added or removed stock for a product")
   @PatchMapping(path = "/{id}/stock")
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> updateStock(
           @RequestBody @Valid ProductStockDTO stock,
           @PathVariable UUID id
   ) {
      log.info("PATCH /{id}/stock called");
      
      ProductResponseDTO product = productsService.addOrRemoveStock(id, stock);
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.stock"), List.of(product));
      return ResponseEntity.ok(response);
   }
   
}
