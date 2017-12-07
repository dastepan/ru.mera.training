package ru.mera.training.shop.dao.dao.impl;

import ru.mera.training.shop.dao.ProductDao;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.List;
import java.util.Set;

public class ProductDaoImpl extends BasicDaoImpl<Product> implements ProductDao {
    public ProductDaoImpl(Class<Product> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return (List<Product>) sessionFactory.getCurrentSession()
                .createQuery("from Product as i where i.name = ?").setParameter(0, name).list()
                ;
    }

    @Override
    public Set<Ingredient> getIngredientSet(int id) {
        return null;
    }

}
