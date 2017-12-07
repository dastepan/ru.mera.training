package ru.mera.training.shop.dao;

import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.List;
import java.util.Set;

public interface ProductDao extends BasicDao<Product>{
    /**
     * method for receiving all products with specified name
     *
     * @return List of products
     **/
    List<Product> getProductsByName(String name);

    /**
     * method to get set of ingredients for specified product
     *
     * @param id product's id to search
     * @return Set of Ingredient
     **/
    Set<Ingredient> getIngredientSet(int id);
}
