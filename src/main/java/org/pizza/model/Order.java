package org.pizza.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    private float totalCoast;
    @ManyToMany(mappedBy = "orders")
    @JsonIgnoreProperties("orders")
    private List<Pizza> pizzas;

    public Order() {
        pizzas=new ArrayList<>();
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }
    public void setPizzas(List<Pizza> pizzaSet) {
        this.pizzas = pizzaSet;
    }
    public float getTotalCoast() {
        totalCoast=0;
            for(Pizza s: pizzas){
                totalCoast+=s.getCost();
            }
        return totalCoast;
    }
    public void setTotalCoast(float totalCoast) {
        this.totalCoast = totalCoast;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;
    }
    @Override
    public int hashCode() {
        return id;
    }
}
