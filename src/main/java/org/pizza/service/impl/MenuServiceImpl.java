package org.pizza.service.impl;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.pizza.model.Ingredient;
import org.pizza.model.Menu;
import org.pizza.model.Menu_;
import org.pizza.model.Pizza;
import org.pizza.repository.impl.MenuRepository;
import org.pizza.service.ServiceCommand;
import org.pizza.service.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;


public class MenuServiceImpl implements ServiceCommand<Menu> {
    private final String INGREDIENT_PATH="ingredients.json";
    private final String MENU_PATH="menu.json";
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private JsonUtil jsonUtil;


    public void init() {
        em=emf.createEntityManager();
        criteriaBuilder=em.unwrap(Session.class).getCriteriaBuilder();


        Menu mainMenu=new Menu();

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
            pizza.setMenu(mainMenu);
            mainMenu.addPizza(pizza);
            em.persist(pizza);
        }
        mainMenu.setName("Main Menu");
        em.persist(mainMenu);
        em.getTransaction().commit();
    }


    MenuServiceImpl(){
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
//    @Transactional(readOnly = true)
    public List<Menu> getAll() {
        CriteriaQuery<Menu> criteriaQuery = criteriaBuilder.createQuery(Menu.class);
        Root<Menu> entityRoot = criteriaQuery.from(Menu.class);
        entityRoot.fetch(Menu_.pizzas, JoinType.LEFT);//присоединяем к записи leftJoin(заполняем список пицц в меню)
        criteriaQuery.select(entityRoot).distinct(true);// distinct-убирает повторы
    return em.createQuery(criteriaQuery).getResultList();
    }


    public List<Menu>getByName(String name){
        return menuRepository.findByName(name);
    }

}
