package org.pizza.repository.impl;

import org.pizza.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IngredientRepository extends JpaRepository<Ingredient,Integer> {
    @Query("select ing from Ingredient ing where ing.name = :name")
    default List<Ingredient> findByName(@Param("name") String name) {
        return null;
    }
}
