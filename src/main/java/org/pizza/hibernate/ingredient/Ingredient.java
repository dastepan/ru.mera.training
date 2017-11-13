package org.pizza.hibernate.ingredient;

import org.json.simple.JSONArray;
import org.pizza.hibernate.pizza.Pizza;
import org.pizza.json.JsonUtil;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "cost", length = 15)
    private float cost;
    @ManyToMany(mappedBy = "ingredients")
    private Set<Pizza> pizzas;


    public Ingredient() {
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    public Set<Pizza> getPizzas() {
        return pizzas;
    }
    public void setPizzas(Set<Pizza> pizzas) {
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
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
}
