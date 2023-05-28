package com.vendingmachine.vendingmachine.domains.order.repositories;

import com.vendingmachine.vendingmachine.domains.order.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
