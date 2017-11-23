package ru.mera.training.shop.dao;

import org.hibernate.Session;
import ru.mera.training.shop.entity.Ingredient;

import java.util.List;

public interface BasicDao<T> {
    /**
     * @return current hibernate session
     **/
    Session getCurrentSession();

    /**
     * method for receiving all entity
     *
     * @return all entity
     **/
    List<T> getList();

    /**
     * method for receiving entity by id
     *
     * @param id = entity id
     * @return specify entity
     **/
    T getById(int id);

    /**
     * add entity to DB
     *
     * @param entity  entity
     * @return specify entity
     **/
    T add(T entity);
    /**
     * update entity in DB
     *
     * @param entity  entity
     * @return specify entity
     **/
    T update(T entity);
    /**
     * delete entity from DB
     *
     * @param entity  entity
     * @return specify entity
     **/
    T delete(Ingredient entity);
}
