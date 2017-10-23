package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.repos.IRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see IRepository
 *
 * @author sergeynazin
 * */

public interface OrderService {
    void save(Order order);
    List<Order> getAll();
    /**
     * This one ONLY when you, say, already POSTed or smth alike, so that entity is supposedly persistent
     */
    Order loadAsPersistent(Long id);
    void update(Order detachedEntity);
    void delete(Order persistentEntity);
    boolean tryDelete(Long id);
    Optional<Order> optionalIsExist(Long id);
}
