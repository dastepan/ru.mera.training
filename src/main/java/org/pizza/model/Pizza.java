package org.pizza.model;

import org.pizza.model.Ingredient;
import org.pizza.model.Menu;
import org.pizza.model.Order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="pizza")
public class Pizza {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;
    @ManyToMany
    @JoinTable(
            name="pizzaAndIngredients",
            joinColumns = {@JoinColumn(name="pizza_id")},
            inverseJoinColumns = {@JoinColumn(name="ingredient_id")})
    private Set<Ingredient> ingredients;
    @ManyToMany
    @JoinTable(
        name = "pizzaAndOrder",
        joinColumns = {@JoinColumn(name="pizza_id")},
        inverseJoinColumns = {@JoinColumn(name="order_id")})
    private Set<Order> orders;
    @Transient
    private float cost;

    public Pizza() {
    }

    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public Set<Order> getOrders() {
        return orders;
    }
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
        float result=0;
        if(!ingredients.isEmpty()) {
            for (Ingredient i : ingredients) {
                result += i.getCost();
            }
        }
        return result;
     }

     public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
     }


}
