package ru.mera.training.shop.service;

import ru.mera.training.shop.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    /**
     * method for receiving all ingredients
     *
     * @return all ingredients
     **/
    List<Ingredient> getAllIngredients();

    /**
     * method for receiving ingredient by id
     *
     * @param id = ingredient id
     * @return Ingredient
     **/
    Ingredient getIngredientById(int id);

    /**
     * method to add new ingredient to DB
     *
     * @param ingredient   ingredient that should be added to DB
     * @return Ingredient
     **/
    Ingredient addIngredient(Ingredient ingredient);
    /**
     * method to update ingredient in DB
     *
     * @param ingredient   ingredient that should be updated in DB
     * @return Ingredient
     **/
    Ingredient updateIngredient(Ingredient ingredient);
    /**
     * method to deleteIngredientById ingredient from DB
     *
     * @param id  ingredient's id that should be deleted from DB
     * @return deleted Ingredient
     **/
    Ingredient deleteIngredient(int id);
    /**
     * method to get list of ingredients which have specified name
     *
     * @param name   ingredient that should be deleted from DB
     * @return List of Ingredients with same name
     **/
    List<Ingredient> getIngredientsByName(String name);
}
