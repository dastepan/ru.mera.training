package org.pizza.repository.impl;

import org.pizza.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IngredientRepository extends JpaRepository<Ingredient,Integer> {
}
