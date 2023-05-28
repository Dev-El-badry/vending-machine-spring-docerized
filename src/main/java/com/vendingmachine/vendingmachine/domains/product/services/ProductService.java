package com.vendingmachine.vendingmachine.domains.product.services;

import com.vendingmachine.vendingmachine.domains.product.daos.ProductDao;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTOMapper;
import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import com.vendingmachine.vendingmachine.domains.product.resources.InsertProductRequest;
import com.vendingmachine.vendingmachine.domains.product.resources.UpdateProductRequest;
import com.vendingmachine.vendingmachine.domains.user.daos.UserDao;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import com.vendingmachine.vendingmachine.exception.NotAllowedException;
import com.vendingmachine.vendingmachine.exception.RequestValidationException;
import com.vendingmachine.vendingmachine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final UserDao userDao;
    private final ProductDTOMapper productDTOMapper;

    public ProductService(@Qualifier("product_jpa") ProductDao productDao, UserDao userDao, ProductDTOMapper productDTOMapper) {
        this.productDao = productDao;
        this.userDao = userDao;
        this.productDTOMapper = productDTOMapper;
    }

    public List<ProductDTO> getProducts() {
        return productDao.getAllProducts().stream().map(productDTOMapper).collect(Collectors.toList());
    }

    public void addProduct(InsertProductRequest request) {
        User user = getUser();

        Product product = new Product(request.name(), request.price(), request.qty(), user);

        productDao.addProduct(product);
    }

    public void updateProduct(UpdateProductRequest request, Integer productId) {
        Product product = productDao.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("product with id [%s] not found".formatted(productId)));

        boolean changes = false;

        if (!request.name().equals(product.getName())) {
            product.setName(request.name());
            changes = true;
        }

        if (!request.price().equals(product.getPrice())) {
            product.setPrice(request.price());
            changes = true;
        }

        if (!request.qty().equals(product.getQty())) {
            product.setQty(request.qty());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        productDao.updateProduct(product);
    }

    public void deleteProduct(Integer id) {
        productDao.deleteProductById(id);
    }

    private User getUser() {
        String username = getUsernameFromSecurityContext();
        return userDao.getUserByUsername(username).orElseThrow(() -> new NotAllowedException("not allowed to be here"));
    }

    private String getUsernameFromSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        return (String) ((UserDetails) principal).getUsername();
    }

    public ProductDTO getProduct(Integer productId) {
        return productDao.getProductById(productId)
                .map(productDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException("product with id [%s] not found".formatted(productId)));
    }
}
