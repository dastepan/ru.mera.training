package org.pizza.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pizza.model.Ingredient;
import org.pizza.model.Menu;
import org.pizza.repository.impl.MenuRepository;
import org.pizza.service.ServiceCommand;
import org.pizza.service.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

public class MenuServiceImpl implements ServiceCommand<Menu> {
    private final String INGREDIENT_PATH="ingredients.json";
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private MenuRepository repository;
    @Autowired
    private JsonUtil jsonUtil;

//    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public void init() {
        em=emf.createEntityManager();
        em.getTransaction().begin();

        JSONArray arrayJson = jsonUtil.readFile(INGREDIENT_PATH);
        for (Object obj : arrayJson) {
            Ingredient ingredient = new Ingredient();
            JSONObject innerObj = (JSONObject) obj;
            ingredient.setCost((float) (long) innerObj.get("cost"));
            ingredient.setName((String) innerObj.get("name"));
            em.persist(ingredient);
        }

        em.getTransaction().commit();
    }

    @Autowired
    public MenuRepository getRepository() {
        return repository;
    }

    @Override
    public void save(Menu entity) {
        repository.saveAndFlush(entity);
    }

    @Override
    public void delete(Menu entity) {
        repository.delete(entity);
    }

    @Override
    public List<Menu> getAll() {
        return repository.findAll();
    }

}
