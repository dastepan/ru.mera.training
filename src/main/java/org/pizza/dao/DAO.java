package org.pizza.dao;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pizza.cntx.Cntx;
import org.pizza.hibernate.HibernateUtil;
import org.pizza.hibernate.ingredient.Ingredient;
import org.pizza.json.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;



public class DAO {
    private String ingredientsPath = "ingredients.json";
    String menuPath= "menu.json";
    @Autowired
    HibernateUtil hibernateUtil;
    @Autowired
    JsonUtil jsonUtil;
    @Autowired
    Cntx cntx;


    public DAO() {
    }

    private void init(){
        Session session=hibernateUtil.getNewSession();
        session.beginTransaction();

        JSONArray arrayJson = jsonUtil.readFile(ingredientsPath);
        for (Object obj : arrayJson) {
            Ingredient ingredient=new Ingredient();
            JSONObject innerObj = (JSONObject) obj;
            ingredient.setCost((float) (long) innerObj.get("cost"));
            ingredient.setName((String) innerObj.get("name"));
            session.save(ingredient);
        }


        session.getTransaction().commit();
    }
}
