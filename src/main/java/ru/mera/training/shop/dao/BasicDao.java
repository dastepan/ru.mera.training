package ru.mera.training.shop.dao;

import org.hibernate.Session;
import ru.mera.training.shop.dto.ProductDTO;
import ru.mera.training.shop.entity.Product;

import java.util.List;
import java.util.Set;

public interface BasicDao<T> {
    /**
     * @return current hibernate session
     **/
    Session getCurrentSession();

    /**
     * method for receiving List of all entity
     *
     * @return List of all entity
     **/
    List<T> getList();

    /**
     * method for receiving Set of all entity
     *
     * @return Set of all entity
     **/
    Set<T> getSet();

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
     * @param entity entity
     * @return specify entity
     **/
    T add(T entity);

    /**
     * update entity in DB
     *
     * @param entity entity
     * @return specify entity
     **/
    T update(T entity);

    /**
     * delete entity from DB
     *
     * @param entity entity
     * @return deleted entity
     **/
    T delete(T entity);

    /**
     * delete Entity By Id from DB
     *
     * @param id
     * @return deleted entity
     **/
    T delete(int id);
}
