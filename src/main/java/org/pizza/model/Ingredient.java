package org.pizza.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@NamedQueries({
        @NamedQuery(name="Ingredient.findByName",
                query="SELECT e FROM Ingredient e WHERE e.name = :name")
})

@Entity
@Table(name = "ingredients")
public class Ingredient implements Serializable{
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
