package ru.mera.training.shop.objects;

import ru.mera.training.shop.objects.interfaces.Describable;

import java.util.Set;

public class Menu implements Describable {
    private int id;
    private Set<Product> productSet;
    private Integer price;

    public Menu() {
    }

    public Menu(int id, Set<Product> productSet) {
        this.id = id;
        this.productSet = productSet;
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

    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getPrice() {
        price = 0;
        for (Product product : productSet) {
            price += product.getCost();
        }
        return price;
    }
}
