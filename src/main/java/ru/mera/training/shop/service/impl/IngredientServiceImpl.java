package ru.mera.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.training.shop.dao.IngredientDao;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.service.IngredientService;

import java.util.List;

@Service("IngredientService")
public class IngredientServiceImpl implements IngredientService{
    @Autowired
    private IngredientDao ingredientDao;

    @Override
    public List<Ingredient> getAllIngredients() {
       return ingredientDao.getList();
    }

    @Override
    public Ingredient getIngredientById(int id) {
        return ingredientDao.getById(id);
    }

    @Override
    public List<Ingredient> getIngredientsByName(String name) {
        return ingredientDao.getIngredientsByName(name);
    }

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientDao.add(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Ingredient ingredient) {
        return ingredientDao.update(ingredient);
    }

    @Override
    public Ingredient deleteIngredient(int id) {
        return ingredientDao.delete(getIngredientById(id));
    }
}
