package com.vendingmachine.vendingmachine.domains.order.dtos;

import com.vendingmachine.vendingmachine.domains.order.entities.Order;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTO;
import com.vendingmachine.vendingmachine.domains.product.dtos.ProductDTOMapper;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTO;
import com.vendingmachine.vendingmachine.domains.user.dtos.UserDTOMapper;
import com.vendingmachine.vendingmachine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class OrderDTOMapper implements Function<Order, OrderDTO> {


    @Override
    public OrderDTO apply(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getAmount()
        );
    }
}
