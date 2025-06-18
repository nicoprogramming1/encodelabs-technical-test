package com.technical_test.encodelabs.dto.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

// activa o desactiva un producto
public record ProductStatusDTO(
        @NotNull
        @Schema(description = "Product status", example = "true")
        boolean status
) {
}
