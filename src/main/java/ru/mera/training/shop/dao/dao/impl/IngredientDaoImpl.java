package ru.mera.training.shop.dao.dao.impl;

import ru.mera.training.shop.dao.IngredientDao;
import ru.mera.training.shop.entity.Ingredient;

import java.util.List;

public class IngredientDaoImpl extends BasicDaoImpl<Ingredient> implements IngredientDao {

    public IngredientDaoImpl(Class<Ingredient> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Ingredient> getIngredientsByName(String name) {
        return (List<Ingredient>) sessionFactory.getCurrentSession()
                .createQuery("from Ingredient as i where i.name = ?").setParameter(0, name).list()
                ;
    }
}
