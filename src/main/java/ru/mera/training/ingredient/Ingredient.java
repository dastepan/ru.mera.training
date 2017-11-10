package ru.mera.training.ingredient;

import ru.mera.training.shaurma.Shaurma;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "cost", length = 15)
    private float cost;
    @ManyToMany(mappedBy = "ingredients")
    private Set<Shaurma> shaurmas;
    public Ingredient() {
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }

    public Set<Shaurma> getShaurmas() {
        return shaurmas;
    }
    public void setShaurmas(Set<Shaurma> shaurmas) {
        this.shaurmas = shaurmas;
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
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }
}
