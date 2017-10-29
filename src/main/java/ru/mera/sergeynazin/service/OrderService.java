package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Again, for educational purposes different approach is used here
 * IT COULD BE GENERIFIED AS WELL AS
 * @see JpaRepository
 *
 * @author sergeynazin
 * */

public interface OrderService {
    void save(Order transientEntity);
    List<Order> getAll();
    Order loadAsPersistent(Long id);
    void update(Order detachedEntity);
    void delete(Order persistentOrDetachedEntity);
    Optional<Order> optionalIsExist(Long id);
    Optional<Order> optionalIsExist(String orderNumber);
}
