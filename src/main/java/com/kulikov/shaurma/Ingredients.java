package com.kulikov.shaurma;

import java.util.HashSet;
import java.util.Set;

public class Ingredients {
    private int id;
    private String name;
    private double cost;
    private Set shaurmas = new HashSet();

    public Ingredients(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Set getShaurmas() {
        return shaurmas;
    }

    public void setShaurmas(Set shaurmas) {
        this.shaurmas = shaurmas;
    }
}
