package org.pizza.service;

import org.pizza.model.Ingredient;

import java.util.List;

public interface IngredientService {
    void save(Ingredient entity);

    void update(Ingredient entity);

    void delete(Ingredient entity);

    List<Ingredient> getAll();
}
