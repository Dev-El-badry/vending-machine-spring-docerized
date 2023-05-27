package com.vendingmachine.vendingmachine.domains.product.repositories;

import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> getProductsByQtyGreaterThan(@Param("qty") Integer qty);
}
