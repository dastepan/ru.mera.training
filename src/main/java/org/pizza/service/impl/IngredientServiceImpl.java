package org.pizza.service.impl;

import org.hibernate.Session;
import org.pizza.model.Ingredient_;
import org.pizza.repository.impl.IngredientRepository;
import org.pizza.model.Ingredient;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class IngredientServiceImpl implements ServiceCommand<Ingredient> {
    @Autowired
    private IngredientRepository repository;
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;

    public void init(){
        em=emf.createEntityManager();
        criteriaBuilder=em.unwrap(Session.class).getCriteriaBuilder();
    }

    @Override
    public void save(Ingredient entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void delete(Ingredient entity) {
        repository.delete(entity);
    }
    @Override
    public List<Ingredient> getAll() {
        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        Root<Ingredient> entityRoot = criteriaQuery.from(Ingredient.class);
        entityRoot.fetch(Ingredient_.pizzas, JoinType.LEFT);//присоединяем к записи leftJoin(заполняем список пицц в меню)
        criteriaQuery.select(entityRoot).distinct(true);// distinct-убирает повторы
        return em.createQuery(criteriaQuery).getResultList();
    }
    @Override
    public void add(Ingredient entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void remove(int id){
        repository.delete(repository.getOne(id));
    }
    @Override
    public Ingredient getById(int id){
        return repository.getOne(id);
    }

    public List<Ingredient> getByName(String name) {
        return repository.findByName(name);
    }


}
