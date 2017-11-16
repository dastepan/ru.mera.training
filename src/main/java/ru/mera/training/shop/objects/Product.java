package ru.mera.training.shop.objects;

import ru.mera.training.shop.objects.interfaces.Describable;

import java.util.Set;

public class Product implements Describable {
    private int id;
    private Set<Ingredient> ingredientsSet;
    private String name;

    public Product(int id, Set<Ingredient> ingredientsSet, String name) {
        this.id = id;
        this.ingredientsSet = ingredientsSet;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Ingredient> getIngredientsSet() {
        return ingredientsSet;
    }

    public void setIngredientsSet(Set<Ingredient> ingredientsSet) {
        this.ingredientsSet = ingredientsSet;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCost() {
        int cost = 0;

        for (Ingredient ingredient : ingredientsSet) {
            cost += ingredient.getCost();
        }
        return cost;
    }
}
