package com.vendingmachine.vendingmachine.domains.product.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdateProductRequest(
        @NotBlank(message = "name must not be empty") String name,
        @Min(value = 1, message = "quantity must be higher 0") Integer price,
        @Min(value = 1, message = "quantity must be higher 0") Integer qty
) {
}
