package com.technical_test.encodelabs.dto.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * el record crea boilerplate automáticamente, podría ponerle msg a cada validación o x defecto
 * Validamos mediante jakarta validation cada input
 */
public record ProductRequestDTO(
        @NotNull
        @Size(min = 3, max = 100)
        @Schema(description = "Product name", example = "Shirt")
        String name,
        
        @Size(max = 300)
        @Schema(description = "Product optional description", example = "White comfortable shirt")
        String description,
        
        @NotNull
        @DecimalMin(value = "0.01", inclusive = true) // por defecto true pero lo dejo por legibilidad
        @Digits(integer = 10, fraction = 2)
        @Schema(description = "Product price amount", example = "50.00")
        BigDecimal priceAmount,
        
        @NotNull
        @Min(0)
        @Schema(description = "Product quantity", example = "10")
        Integer quantity
) {
}
