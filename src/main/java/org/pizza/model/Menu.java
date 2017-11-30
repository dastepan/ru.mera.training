package org.pizza.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="menu")
public class Menu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @JsonIgnoreProperties("menu")
    private List<Pizza> pizzas;

    public Menu() {
        pizzas=new ArrayList<>();
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void addPizza(Pizza pizza){
        pizzas.add(pizza);
    }
    @Override
    public int hashCode() {
        return id;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        return pizzas != null ? pizzas.equals(menu.pizzas) : menu.pizzas == null;
    }
}


