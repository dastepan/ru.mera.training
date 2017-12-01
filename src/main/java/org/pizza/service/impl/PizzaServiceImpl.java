package org.pizza.service.impl;

import org.hibernate.Session;
import org.pizza.model.Pizza;
import org.pizza.model.Pizza_;
import org.pizza.model.supportClasses.Ingredient_;
import org.pizza.repository.impl.PizzaRepository;
import org.pizza.service.ServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class PizzaServiceImpl implements ServiceCommand<Pizza> {

    @Autowired
    private PizzaRepository repository;//внизу Autowire над getter
    @Autowired
    private EntityManagerFactory emf;
    private EntityManager em;
    private CriteriaBuilder criteriaBuilder;

    public void init(){
        em=emf.createEntityManager();
        criteriaBuilder=em.unwrap(Session.class).getCriteriaBuilder();
    }
    PizzaServiceImpl(){
    }

    @Override
    public void save(Pizza entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void delete(Pizza entity) {
        repository.delete(entity);
    }
    @Override
    public List<Pizza> getAll() {
        CriteriaQuery<Pizza> criteriaQuery = criteriaBuilder.createQuery(Pizza.class);
        Root<Pizza> entityRoot = criteriaQuery.from(Pizza.class);
        entityRoot.fetch(Pizza_.ingredients, JoinType.LEFT);//присоединяем к записи leftJoin(заполняем список пицц в меню)
        entityRoot.fetch(Pizza_.menu, JoinType.LEFT);//присоединяем к записи leftJoin(заполняем список пицц в меню)
        criteriaQuery.select(entityRoot).distinct(true);// distinct-убирает повторы
        return em.createQuery(criteriaQuery).getResultList();
    }
    @Override
    public void add(Pizza entity) {
        repository.saveAndFlush(entity);
    }
    @Override
    public void remove(int id){
        repository.delete(repository.getOne(id));
    }
    @Override
    public Pizza getById(int id){
        return repository.getOne(id);
    }

    public List<Pizza> getByName(String name) {
        return repository.findByName(name);
    }

}
