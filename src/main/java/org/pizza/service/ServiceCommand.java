package org.pizza.service;


import java.util.List;

public interface ServiceCommand<T> {
    void save(T entity);

    void delete(T entity);

    List<T> getAll();

    void add(T entity);

    void remove(int i);

    T getById(int i);

}
