package ru.mera.sergeynazin.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Objects;

// FIXME: 10/20/17 hashCode + equals
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(length = 45)
    @Nationalized
    private String name;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2)
    private Double cost;


    // TODO: 10/23/17 Do I need it empty constructor?
    public Ingredient() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Ingredient ingredient = (Ingredient) o;

        return Objects.equals(this.name, ingredient.name)
            && Objects.equals(this.id, ingredient.id)
            && Objects.equals(this.cost, ingredient.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id, this.cost);
    }

}
