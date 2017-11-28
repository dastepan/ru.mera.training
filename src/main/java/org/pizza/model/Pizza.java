package org.pizza.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="pizza")
public class Pizza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="menuId")
    private Menu menu;
    @ManyToMany
    @JoinTable(
            name="pizzaAndIngredients",
            joinColumns = {@JoinColumn(name="pizza_id")},
            inverseJoinColumns = {@JoinColumn(name="ingredient_id")})
    private List<Ingredient> ingredients;
    @ManyToMany
    @JoinTable(
        name = "pizzaAndOrder",
        joinColumns = {@JoinColumn(name="pizza_id")},
        inverseJoinColumns = {@JoinColumn(name="order_id")})
    private List<Order> orders;
    @Transient
    private float cost;

    public Pizza() {
        orders=new ArrayList<>();
        ingredients=new ArrayList<>();
    }

    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pizza pizza = (Pizza) o;

        if (id != pizza.id) return false;
        if (Float.compare(pizza.cost, cost) != 0) return false;
        if (!name.equals(pizza.name)) return false;
        return ingredients != null ? ingredients.equals(pizza.ingredients) : pizza.ingredients == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
