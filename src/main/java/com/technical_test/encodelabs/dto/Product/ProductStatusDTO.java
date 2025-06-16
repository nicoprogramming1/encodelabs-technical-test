package com.technical_test.encodelabs.dto.Product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record ProductStatusDTO(
        @NotNull
        @Schema(description = "Product name", example = "Shirt")
        boolean status
) {
}
