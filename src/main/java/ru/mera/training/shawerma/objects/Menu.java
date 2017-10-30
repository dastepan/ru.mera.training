package ru.mera.training.shawerma.objects;

import ru.mera.training.shawerma.objects.interfaces.Describable;

import java.util.Set;

public class Menu implements Describable {
    private int id;
    private Set<Shaurma> shaurmaSet;
    private int price;

    public Menu() {
    }

    public Menu(int id, Set<Shaurma> shaurmaSet) {
        this.id = id;
        this.shaurmaSet = shaurmaSet;
        this.price = getPrice();
    }
    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getCost() {
        return getPrice();
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Shaurma> getShaurmaSet() {
        return shaurmaSet;
    }

    public void setShaurmaSet(Set<Shaurma> shaurmaSet) {
        this.shaurmaSet = shaurmaSet;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        price = 0;
        for (Shaurma shaurma : shaurmaSet) {
            price += shaurma.getCost();
        }
        return price;
    }
}
