package ru.mera.training.shawerma.objects;

import ru.mera.training.shawerma.objects.interfaces.Describable;

import java.util.Set;

public class Menu implements Describable {
    private int id;
    private Set<Shaurma> shaurmaSet;
    private Integer price;

    public Menu() {
    }

    public Menu(int id, Set<Shaurma> shaurmaSet) {
        this.id = id;
        this.shaurmaSet = shaurmaSet;
        this.price = getPrice();
    }
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Integer getCost() {
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

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        price = 0;
        for (Shaurma shaurma : shaurmaSet) {
            price += shaurma.getCost();
        }
        return price;
    }
}
