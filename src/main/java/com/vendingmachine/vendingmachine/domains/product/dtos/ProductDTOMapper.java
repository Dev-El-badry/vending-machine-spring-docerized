package com.vendingmachine.vendingmachine.domains.product.dtos;

import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDTOMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQty()
        );
    }
}
