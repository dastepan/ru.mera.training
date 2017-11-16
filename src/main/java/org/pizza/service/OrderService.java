package org.pizza.service;

import org.pizza.model.Order;

import java.util.List;

interface  OrderService {
    void save(Order entity);

    void update(Order entity);

    void delete(Order entity);

    List<Order> getAll();
}
