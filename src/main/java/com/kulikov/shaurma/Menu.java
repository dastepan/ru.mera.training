package com.kulikov.shaurma;

/*import java.util.List;
import java.util.ArrayList;*/

import java.util.HashSet;
import java.util.Set;

public class Menu {
    private int id;
    private int shaurmaId;
    private double price;
    private Set menuShaurma= new HashSet();

    public Menu(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShaurmaId() {
        return shaurmaId;
    }

    public void setShaurmaId(int shaurmaId) {
        this.shaurmaId = shaurmaId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set getMenuShaurma() {
        return menuShaurma;
    }

    public void setMenuShaurma(Set menuShaurma) {
        this.menuShaurma = menuShaurma;
    }
}
