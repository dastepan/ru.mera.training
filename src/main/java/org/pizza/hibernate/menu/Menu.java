package org.pizza.hibernate.menu;

import org.pizza.hibernate.pizza.Pizza;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="menu")
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Column(name = "price")
    private float price;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myMenu")
    private List<Pizza> pizzas;


    public Menu() {
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


}


