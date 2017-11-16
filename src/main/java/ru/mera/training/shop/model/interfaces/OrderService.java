package ru.mera.training.shop.model.interfaces;

import ru.mera.training.shop.objects.Order;
import ru.mera.training.shop.objects.Product;

import java.util.List;

// TODO для управления заказами предусмотреть разделение пользовательсих прав
public interface OrderService {
    boolean addToOrder(Product product);

    boolean addToOrder(List<Product> productList);

    boolean sendOrder(Order order);

    void showOrderList();

    boolean confirmOrder();

    boolean cancelOrder();

    boolean closeOrder();

    boolean saveOrder();

}
