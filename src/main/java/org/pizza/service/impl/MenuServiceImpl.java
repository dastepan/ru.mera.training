package org.pizza.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pizza.model.Ingredient;
import org.pizza.model.Menu;
import org.pizza.model.Pizza;
import org.pizza.repository.impl.MenuRepository;
import org.pizza.service.ServiceCommand;
import org.pizza.service.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.*;

public class MenuServiceImpl implements ServiceCommand<Menu> {
    private final String INGREDIENT_PATH="ingredients.json";
    private final String MENU_PATH="menu.json";
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private MenuRepository menuRepository;//тянется через геттер
    @Autowired
    private JsonUtil jsonUtil;

//    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public void init() {
        Menu mainMenu=new Menu();
        em=emf.createEntityManager();
        em.getTransaction().begin();
        HashMap<String,Ingredient> initIngredientsMap=new HashMap<>();
        for (Object obj : jsonUtil.readFile(INGREDIENT_PATH)) {
            Ingredient ingredient = new Ingredient();
            JSONObject jsonIngr = (JSONObject) obj;
            ingredient.setCost((float)(long) jsonIngr.get("cost"));
            ingredient.setName((String) jsonIngr.get("name"));
            initIngredientsMap.put(ingredient.getName(),ingredient);
            em.persist(ingredient);
        }

        for (Object obj : jsonUtil.readFile(MENU_PATH)) {
            Pizza pizza = new Pizza();
            JSONObject jsonPizza = (JSONObject) obj;
            for(Object ingrName: (JSONArray)jsonPizza.get("ingredients")){
                String nameIng=ingrName.toString();
                Ingredient ingredient=initIngredientsMap.get(nameIng);
                ingredient.addPizza(pizza);
                pizza.addIngredient(ingredient);
            }
            pizza.setName((String) jsonPizza.get("name"));
            mainMenu.addPizza(pizza);
            em.persist(pizza);
        }
        mainMenu.setName("Main Menu");
        em.persist(mainMenu);
        em.getTransaction().commit();
    }

    @Autowired
    public MenuRepository getRepository() {
        return menuRepository;
    }

    @Override
    public void save(Menu entity) {
        menuRepository.saveAndFlush(entity);
    }

    @Override
    public void delete(Menu entity) {
        menuRepository.delete(entity);
    }

    @Override
    public List<Menu> getAll() {
        return menuRepository.findAll();
    }

}
