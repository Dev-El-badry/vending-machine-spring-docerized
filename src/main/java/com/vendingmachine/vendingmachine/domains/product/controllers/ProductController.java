package com.vendingmachine.vendingmachine.domains.product.controllers;

import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.resources.InsertProductRequest;
import com.vendingmachine.vendingmachine.domains.product.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getProduct() {
        return productService.getProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody InsertProductRequest request) {
        productService.addProduct(request);
    }
}
