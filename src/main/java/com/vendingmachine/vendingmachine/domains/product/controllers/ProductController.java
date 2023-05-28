package com.vendingmachine.vendingmachine.domains.product.controllers;

import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.resources.InsertProductRequest;
import com.vendingmachine.vendingmachine.domains.product.resources.UpdateProductRequest;
import com.vendingmachine.vendingmachine.domains.product.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Integer productId) {
        ProductDTO response = productService.getProduct( productId);
        return ResponseEntity.ok()
                .body(response);
    }

    @PostMapping
    public void addProduct(@Valid @RequestBody InsertProductRequest request) {
        productService.addProduct(request);
    }

    @PutMapping("{productId}")
    public void updateProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody UpdateProductRequest request) {
        productService.updateProduct(request, productId);
    }

    @DeleteMapping("{productId}")
    public void deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProduct(productId);
    }

}
