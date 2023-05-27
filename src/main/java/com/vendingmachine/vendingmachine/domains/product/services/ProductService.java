package com.vendingmachine.vendingmachine.domains.product.services;

import com.vendingmachine.vendingmachine.domains.product.daos.ProductDao;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTOMapper;
import com.vendingmachine.vendingmachine.domains.product.resources.InsertProductRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final ProductDTOMapper productDTOMapper;

    public ProductService(@Qualifier("product_jpa") ProductDao productDao, ProductDTOMapper productDTOMapper) {
        this.productDao = productDao;
        this.productDTOMapper = productDTOMapper;
    }

    public List<ProductDTO> getProducts() {
        return productDao.getAllProducts().stream().map(productDTOMapper).collect(Collectors.toList());
    }

    public void addProduct(InsertProductRequest request) {

    }
}
