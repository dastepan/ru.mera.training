package ru.mera.training.shop.dao;

import ru.mera.training.shop.entity.Ingredient;

import java.util.List;

public interface IngredientDao extends BasicDao<Ingredient>{

    /**
     * method for receiving all ingredients with specified name
     *
     * @return List of ingredients
     **/
    List<Ingredient> getIngredientsByName(String name);

}
