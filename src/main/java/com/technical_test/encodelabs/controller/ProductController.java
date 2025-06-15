package com.technical_test.encodelabs.controller;

import com.technical_test.encodelabs.dto.ApiResponseDTO;
import com.technical_test.encodelabs.dto.Product.ProductResponseDTO;
import com.technical_test.encodelabs.service.MessageService;
import com.technical_test.encodelabs.service.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
   @ApiResponses({
           @ApiResponse(responseCode = "200", description = "Products active/inactive retrieved",
                   content = @Content(
                           array = @ArraySchema(
                                   schema = @Schema(implementation = ProductResponseDTO.class)))),
           @ApiResponse(responseCode = "500", description = "Server internal error", content = @Content)
   })
   public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> products() {
      log.info("GET /products called");
      
      List<ProductResponseDTO> products = productsService.retrieveAll();
      ApiResponseDTO<List<ProductResponseDTO>> response =
              ApiResponseDTO.success(msgService.get("product.list"), products);
      
      return ResponseEntity.ok(response);
   }
}
