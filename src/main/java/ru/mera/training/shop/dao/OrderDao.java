package ru.mera.training.shop.dao;

import ru.mera.training.shop.entity.Order;

public interface OrderDao extends BasicDao<Order> {

    /**
     * method for receiving order with specified id
     *
     * @param id - specified id
     *
     * @return Order
     **/
    Order getOrderById(String id);
}
