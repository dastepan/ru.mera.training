package ru.mera.training.shop.service;

import ru.mera.training.shop.entity.Order;

import java.util.List;

public interface OrderService {
    /**
     * method for receiving all orders
     *
     * @return all orders
     **/
    List<Order> getAllOrders();

    /**
     * method for receiving order by id
     *
     * @param id = order id
     * @return Order
     **/
    Order getOrderById(String id);

    /**
     * method to add new order to DB
     *
     * @param order order that should be added to DB
     * @return Order
     **/
    Order addOrder(Order order);

    /**
     * method to update order in DB
     *
     * @param order order that should be updated in DB
     * @return Order
     **/
    Order updateOrder(Order order);

    /**
     * method to delete Order By Id from DB
     *
     * @param id order's id that should be deleted from DB
     * @return deleted Order
     **/
    Order deleteOrder(int id);
}
