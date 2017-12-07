package ru.mera.training.shop.dto;

import org.junit.Test;
import ru.mera.training.shop.entity.Ingredient;

import static org.junit.Assert.*;

public class IngredientDTOUnitTest {
    @Test
    public void getIngredientDTO() throws Exception {
        Ingredient ingredient = createIngredient();
    }

    private Ingredient createIngredient() {
        Ingredient ingredient  = new Ingredient();
        ingredient.setId(1);
        return null;
    }

}