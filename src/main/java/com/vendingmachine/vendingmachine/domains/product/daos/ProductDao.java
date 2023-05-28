package com.vendingmachine.vendingmachine.domains.product.daos;

import com.vendingmachine.vendingmachine.domains.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getAllProducts();
    void addProduct(Product product);
    void updateProduct(Product product);
    Optional<Product> getProductById(Integer id);
    void deleteProductById(Integer id);
}
