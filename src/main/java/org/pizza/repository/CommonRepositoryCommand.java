package org.pizza.repository;


import java.io.Serializable;
import java.util.List;

public interface CommonRepositoryCommand {

//C
    <T> Serializable create(final T transientEntity);
//R
    <T> List<T> getByName(String name);
    <T> T getById(int id);
    <T> T getById(Serializable id);
//U
    <T> void update(T entity);
//D
    <T> void delete(T entity);

}
