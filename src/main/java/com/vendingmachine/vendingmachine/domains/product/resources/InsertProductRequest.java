package com.vendingmachine.vendingmachine.domains.product.resources;

public record InsertProductRequest(String name, Integer price, Integer qty) {
}
