package org.pizza.hibernate.menu;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pizza.cntx.Cntx;
import org.pizza.hibernate.HibernateUtil;
import org.pizza.hibernate.ingredient.Ingredient;
import org.pizza.hibernate.pizza.Pizza;
import org.pizza.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

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

    @Transient
    String ingredients= "ingredients.json";
    @Transient
    String menu= "menu.json";
    @Transient
    @Autowired
    HibernateUtil hibernateUtil;
    @Transient
    @Autowired
    JsonUtil jsonUtil;
    @Transient
    @Autowired
    Cntx cntx;


    public Menu() {
    }

    private void init(){
        JSONArray arrayJson = jsonUtil.readFile(ingredients);
        for (Object obj : arrayJson) {
            JSONObject innerObj = (JSONObject) obj;
            Ingredient ingredient=(Ingredient) cntx.getContext().getBean(Ingredient.class);
            ingredient.setCost((float)(long)innerObj.get("cost"));
            ingredient.setName((String) innerObj.get("name"));
        }
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


