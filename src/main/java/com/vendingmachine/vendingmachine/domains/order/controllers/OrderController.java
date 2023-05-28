package com.vendingmachine.vendingmachine.domains.order.controllers;

import com.vendingmachine.vendingmachine.domains.order.dtos.OrderDTO;
import com.vendingmachine.vendingmachine.domains.order.resources.CreateOrderRequest;
import com.vendingmachine.vendingmachine.domains.order.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> buy(@Valid @RequestBody CreateOrderRequest request) {
        HashMap<String, Object> response = orderService.buy(request);

        return ResponseEntity.ok()
                .body(response);
    }
}
