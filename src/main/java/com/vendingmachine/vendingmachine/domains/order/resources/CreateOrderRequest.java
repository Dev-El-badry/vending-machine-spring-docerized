package com.vendingmachine.vendingmachine.domains.order.resources;

import jakarta.validation.constraints.Min;

public record CreateOrderRequest(Integer product_id,
                                 @Min(value = 1, message = "quantity must be higher 0") Integer qty) {
}
