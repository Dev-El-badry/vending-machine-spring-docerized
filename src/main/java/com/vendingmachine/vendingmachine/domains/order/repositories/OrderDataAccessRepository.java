package com.vendingmachine.vendingmachine.domains.order.repositories;

import com.vendingmachine.vendingmachine.domains.order.daos.OrderDao;
import com.vendingmachine.vendingmachine.domains.order.entities.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDataAccessRepository implements OrderDao {
    private final OrderRepository orderRepository;

    public OrderDataAccessRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }
}
