package ru.mera.training.shop.model.interfaces;

import ru.mera.training.shop.objects.Ingredient;
import ru.mera.training.shop.objects.Product;

// TODO реализовать проверку админских прав для критических изменений
public interface ProductEdit {
    boolean save(Product product);

    boolean delete(Product product);

    boolean edit(Product product);

    boolean edit(Product product, Ingredient ingredient);

    boolean add(Product product);

    boolean add(Product product, Ingredient ingredient);

}
