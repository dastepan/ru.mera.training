package ru.mera.training.shawerma.objects;


import ru.mera.training.shawerma.objects.interfaces.Describable;

public class Order implements Describable {
    private String orderNumber; // дата + число
    private int totalCost;
//    private String ordercol;

    public Order() {
    }

    public Order(String orderNumber, int totalCost) {
        this.orderNumber = orderNumber;
        this.totalCost = totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public int getId() {
        // TODO распарсить строку в число типа int
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getCost() {
        return totalCost;
    }
}
