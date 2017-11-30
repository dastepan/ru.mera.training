package ru.mera.training.shop.dao.dao.impl;

import ru.mera.training.shop.dao.OrderDao;
import ru.mera.training.shop.entity.Order;

public class OrderDaoImpl extends BasicDaoImpl<Order> implements OrderDao {
    public OrderDaoImpl(Class<Order> entityClass) {
        super(entityClass);
    }

//    @Override
//    public Order getOrderById(String id) {
//        return (Order) sessionFactory.getCurrentSession()
//                .createQuery("select o from Order o where o.orderNumber = :id").setParameter("id", id).uniqueResult();
//    }
}
