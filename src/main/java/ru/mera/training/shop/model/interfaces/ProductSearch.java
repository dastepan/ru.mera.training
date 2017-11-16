package ru.mera.training.shop.model.interfaces;

import ru.mera.training.shop.objects.Ingredient;
import ru.mera.training.shop.objects.Product;

import java.util.Set;

public interface ProductSearch {
    Set<Product> getProduct(); // весь список

    Set<Product> getProduct(String name); // поиск пр названию

    Set<Product> getProduct(Integer id); // поиск по id

    Set<Product> getProduct(Ingredient ingredient); // споисок, включающий заданный ингредиент

}
