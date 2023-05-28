package com.vendingmachine.vendingmachine.domains.order.daos;

import com.vendingmachine.vendingmachine.domains.order.entities.Order;

public interface OrderDao {
    void saveOrder(Order order);
}
