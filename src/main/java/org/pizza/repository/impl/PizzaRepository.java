package org.pizza.repository.impl;

import org.pizza.repository.CommonRepositoryCommand;

import java.io.Serializable;
import java.util.List;

public class PizzaRepository implements CommonRepositoryCommand {


    @Override
    public <T> Serializable create(T transientEntity) {
        return null;
    }

    @Override
    public <T> List<T> getByName(String name) {
        return null;
    }

    @Override
    public <T> T getById(int id) {
        return null;
    }

    @Override
    public <T> T getById(Serializable id) {
        return null;
    }

    @Override
    public <T> void update(T entity) {

    }

    @Override
    public <T> void delete(T entity) {

    }
}
