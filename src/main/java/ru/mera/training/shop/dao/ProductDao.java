package ru.mera.training.shop.dao;

import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.List;

public interface ProductDao extends BasicDao<Product>{
    /**
     * method for receiving all products with specified name
     *
     * @return List of products
     **/
    List<Product> getProductsByName(String name);
}
