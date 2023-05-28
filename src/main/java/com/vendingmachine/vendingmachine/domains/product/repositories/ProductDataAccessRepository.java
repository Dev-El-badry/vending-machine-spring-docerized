package com.vendingmachine.vendingmachine.domains.product.repositories;

import com.vendingmachine.vendingmachine.domains.product.daos.ProductDao;
import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public void deleteProductById(Integer id) {
        productRepository.deleteProductById(id);
    }
}
