package org.pizza.service.impl;

import org.pizza.model.Order;

import org.pizza.repository.impl.OrderRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements ServiceCommand<Order> {
    private OrderRepository repository;//внизу Autowire над getter


    @Override
    public void save(Order entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Order entity) {
        repository.delete(entity);
    }

    @Override
    public List<Order> getAll() {
        return repository.findAll();
    }

    @Autowired
    public OrderRepository getRepository() {
        return repository;
    }
}
