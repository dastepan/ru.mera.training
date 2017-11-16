package org.pizza.model;


import org.pizza.service.utilities.AutoIncrementer;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name="orders")
public class Order {
    @Transient
    private AutoIncrementer autoIncrement;
    @Id
    @GeneratedValue
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

}
