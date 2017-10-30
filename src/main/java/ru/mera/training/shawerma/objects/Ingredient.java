package ru.mera.training.shawerma.objects;

import ru.mera.training.shawerma.objects.interfaces.Describable;

public class Ingredient implements Describable {
    private int id;
    private String name;
    private int cost;

    public Ingredient() {
    }

    public Ingredient(int id, String name, int cost) {
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
