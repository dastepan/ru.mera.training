package ru.mera.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.training.shop.dao.OrderDao;
import ru.mera.training.shop.entity.Order;
import ru.mera.training.shop.service.OrderService;

import java.util.List;

@Service("OrderService")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> getAllOrders() {
        return orderDao.getList();
    }

    @Override
    public Order getOrderById(int id) {
        return orderDao.getById(id);
    }

    @Override
    public Order addOrder(Order order) {
        return orderDao.add(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return orderDao.update(order);
    }

    @Override
    public Order deleteOrder(int id) {
        return orderDao.delete(getOrderById(id));
    }
}
