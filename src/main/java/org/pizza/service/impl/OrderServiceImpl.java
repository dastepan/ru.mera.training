package org.pizza.service.impl;

import org.pizza.model.Order;

import org.pizza.repository.impl.OrderRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderServiceImpl implements ServiceCommand<Order> {
    @Autowired
    private OrderRepository repository;

    public void init(){
    }
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
    @Override
    public void add(Order entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void remove(int id){
        repository.delete(repository.getOne(id));
    }
    @Override
    public Order getById(int id){
        return repository.getOne(id);
    }
}
