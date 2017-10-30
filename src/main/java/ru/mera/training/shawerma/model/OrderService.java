package ru.mera.training.shawerma.model;

import ru.mera.training.shawerma.objects.Order;
import ru.mera.training.shawerma.objects.Shaurma;

import java.util.List;

// TODO для управления заказами предусмотреть разделение пользовательсих прав
public interface OrderService {
    boolean addToOrder(Shaurma shaurma);

    boolean addToOrder(List<Shaurma> shaurmaList);

    boolean sendOrder(Order order);

    void showOrderList();

    boolean confirmOrder();

    boolean cancelOrder();

    boolean closeOrder();

    boolean saveOrder();

}
