package com.technical_test.encodelabs.dto.Product;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        
        @Schema(description = "Product unique UUID", example = "a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a1")
        UUID id,
        
        @Schema(description = "Product name", example = "Shirt")
        String name,
        
        @Schema(description = "Product optional description", example = "White comfortable shirt")
        String description,
        
        @Schema(description = "Product price amount", example = "50.00")
        BigDecimal priceAmount,
        
        @Schema(description = "", example = "")
        String currencyCode,
        
        @Schema(description = "Product quantity", example = "10")
        Integer quantity,
        
        @Schema(description = "Product status", example = "active / inactive")
        boolean isActive,
        
        @Schema(description = "Product last updated date", example = "dd-MM-yyyy HH:mm:ss")
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss", timezone = "America/Argentina/Buenos_Aires")
        LocalDateTime updatedAt
) {
}
