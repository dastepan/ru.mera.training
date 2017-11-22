package org.pizza.model;


import org.pizza.service.utilities.AutoIncrementer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name="orders")
public class Order implements Serializable {
    @Transient
    private AutoIncrementer autoIncrement;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String orderNumber;
    private float totalCoast;
    @ManyToMany(mappedBy = "orders")
    private Set<Pizza> pizzaSet;


    public Order() {
    }

    public void init(){
        orderNumber=new Date(System.currentTimeMillis())+" ";
        orderNumber=orderNumber+ autoIncrement.getNumber();
    }

    public Set<Pizza> getPizzaSet() {
        return pizzaSet;
    }

    public void setPizzaSet(Set<Pizza> pizzaSet) {
        this.pizzaSet = pizzaSet;
    }

    public float getTotalCoast() {
        totalCoast=0;
            for(Pizza s:pizzaSet){
                totalCoast+=s.getCost();
            }
        return totalCoast;
    }
    public void setTotalCoast(float totalCoast) {
        this.totalCoast = totalCoast;
    }
    public AutoIncrementer getAutoIncrement() {
        return autoIncrement;
    }
    public void setAutoIncrement(AutoIncrementer autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Float.compare(order.totalCoast, totalCoast) != 0) return false;
        if (!orderNumber.equals(order.orderNumber)) return false;
        return pizzaSet != null ? pizzaSet.equals(order.pizzaSet) : order.pizzaSet == null;
    }

    @Override
    public int hashCode() {
        return orderNumber.hashCode();
    }
}
