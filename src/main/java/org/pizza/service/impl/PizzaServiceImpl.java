package org.pizza.service.impl;

import org.pizza.model.Pizza;
import org.pizza.repository.impl.PizzaRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PizzaServiceImpl implements ServiceCommand<Pizza> {
    private PizzaRepository repository;//внизу Autowire над getter

    @Override
    public void save(Pizza entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Pizza entity) {
        repository.delete(entity);
    }

    @Override
    public List<Pizza> getAll() {
        return repository.findAll();
    }

    public List<Pizza> getByName(String name) {
        return repository.findByName(name);
    }

    @Autowired
    public PizzaRepository getRepository() {
        return repository;
    }
}
