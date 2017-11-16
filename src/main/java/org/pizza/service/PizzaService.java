package org.pizza.service;

import org.pizza.model.Pizza;

import java.util.List;

public interface PizzaService {
    void save(Pizza entity);

    void update(Pizza entity);

    void delete(Pizza entity);

    List<Pizza> getAll();
}
