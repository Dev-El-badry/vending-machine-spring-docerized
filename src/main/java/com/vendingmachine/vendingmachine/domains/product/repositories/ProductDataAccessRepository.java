package com.vendingmachine.vendingmachine.domains.product.repositories;

import com.vendingmachine.vendingmachine.domains.product.daos.ProductDao;
import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("product_jpa")
public class ProductDataAccessRepository implements ProductDao {
    private final ProductRepository productRepository;

    public ProductDataAccessRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getProductsByQtyGreaterThan(0);
    }

    @Override
    public void addProduct(Product product) {

    }
}
