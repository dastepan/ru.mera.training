package org.pizza.service;

import java.util.List;

public interface ServiceCommand<T> {
    void save(T entity);

    void update(T entity);

    void delete(T entity);

    List<T> getAll();

    List<T> getByName(String name);
}
