package ru.mera.training.shawerma.objects;

import ru.mera.training.shawerma.objects.interfaces.Describable;

import java.util.Set;

public class Shaurma implements Describable {
    private int id;
    private Set<Ingredient> ingredientsSet;
    private String name;

    public Shaurma(int id, Set<Ingredient> ingredientsSet, String name) {
        this.id = id;
        this.ingredientsSet = ingredientsSet;
        this.name = name;
    }

    @Override
    public Integer getId() {
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
    public Integer getCost() {
        int cost = 0;

        for (Ingredient ingredient : ingredientsSet) {
            cost += ingredient.getCost();
        }
        return cost;
    }
}
