package com.vendingmachine.vendingmachine.domains.product.daos;

import com.vendingmachine.vendingmachine.domains.product.entities.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
}
