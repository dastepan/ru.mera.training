package org.pizza.hibernate.menu;

import org.json.simple.JSONArray;
import org.pizza.hibernate.pizza.Pizza;
import org.pizza.json.JsonUtil;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="menu")
public class Menu {
    String FILE_PATH = "menu.txt";
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

    public Menu(JsonUtil jsonUtil) {
        JSONArray arrayJson = jsonUtil.readFile(ingredients);
//        for (Object anArrayJson : arrayJson) {
//            JSONObject innerObj = (JSONObject) anArrayJson;
//            System.out.println("name" + innerObj.get("name") +
//                    " cost " + innerObj.get("cost"));
//        }
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


