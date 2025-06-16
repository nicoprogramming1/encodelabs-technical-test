package com.technical_test.encodelabs.dto.Product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ProductStockDTO(
        @Min(value = -1000)
        @Max(value = 1000)
        @NotNull
        Integer stock
) {
}
