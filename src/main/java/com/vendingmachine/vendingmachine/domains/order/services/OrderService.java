package com.vendingmachine.vendingmachine.domains.order.services;

import com.vendingmachine.vendingmachine.domains.order.daos.OrderDao;
import com.vendingmachine.vendingmachine.domains.order.dtos.OrderDTO;
import com.vendingmachine.vendingmachine.domains.order.dtos.OrderDTOMapper;
import com.vendingmachine.vendingmachine.domains.order.entities.Order;
import com.vendingmachine.vendingmachine.domains.order.resources.CreateOrderRequest;
import com.vendingmachine.vendingmachine.domains.product.daos.ProductDao;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTOMapper;
import com.vendingmachine.vendingmachine.domains.product.entities.Product;
import com.vendingmachine.vendingmachine.domains.user.daos.UserDao;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTOMapper;
import com.vendingmachine.vendingmachine.domains.user.entities.User;
import com.vendingmachine.vendingmachine.exception.NotAllowedException;
import com.vendingmachine.vendingmachine.exception.RequestValidationException;
import com.vendingmachine.vendingmachine.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final OrderDTOMapper orderDTOMapper;

    private final ProductDTOMapper productDTOMapper;
    private final UserDTOMapper userDTOMapper;

    private static final List<Integer> banknotes = List.of(100, 50, 20, 10, 5);


    public OrderService(OrderDao orderDao, ProductDao productDao, UserDao userDao, OrderDTOMapper orderDTOMapper, ProductDTOMapper productDTOMapper, UserDTOMapper userDTOMapper) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.orderDTOMapper = orderDTOMapper;
        this.productDTOMapper = productDTOMapper;
        this.userDTOMapper = userDTOMapper;
    }


    public HashMap<String, Object> buy(CreateOrderRequest request) {
        Product product = getProductById(request.product_id());
        User user = getUser();

        Integer total = product.getPrice() * request.qty();

        if(product.getQty() < request.qty()) {
            throw new RequestValidationException("don't enough products");
        }

        if (user.getDeposit() < total) {
            throw new RequestValidationException("you don't have enough money to buy");
        }

        Order order = new Order(
                total,
                user,
                product
        );

        orderDao.saveOrder(order);

        product.setQty(product.getQty() - request.qty());
        productDao.updateProduct(product);

        Integer deposit = user.getDeposit() - total;
        user.setDeposit(deposit);
        userDao.updateUser(user);

        List<Integer> changes = getRest(deposit);

        ProductDTO productDto = Optional.of(product).map(productDTOMapper).orElseThrow();
        UserDTO userDTO = Optional.of(user).map(userDTOMapper).orElseThrow();
        OrderDTO orderDTO = Optional.of(order).map(orderDTOMapper).stream().findFirst().orElseThrow();

        HashMap<String, Object> res = new HashMap<>();
        res.put("product", productDto);
        res.put("user", userDTO);
        res.put("order", orderDTO);
        res.put("changes", changes);

        return res;
    }

    private static List<Integer> getRest(Integer total) {
        List<Integer> changes = new ArrayList<>();

        int i = 0;
        while (total > 4) {
            int x = banknotes.get(i);

            if(total >= x) {
                changes.add(x);
                total = total - x;
                i = 0;
            }  else {
                i ++;
            }
        }

        return changes;
    }

    private Product getProductById(Integer productId) {
        return productDao.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("not found product with id [%s]".formatted(productId)));
    }

    private User getUser() {
        String username = getUsernameFromSecurityContext();
        return userDao.getUserByUsername(username).orElseThrow(() -> new NotAllowedException("not allowed to be here"));
    }

    private String getUsernameFromSecurityContext() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();

        return (String)
                ((UserDetails) principal).getUsername();
    }
}
