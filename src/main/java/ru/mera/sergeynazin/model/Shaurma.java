package ru.mera.sergeynazin.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.DoubleAdder;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(value = { "cost" }, ignoreUnknown = true)
@Access(AccessType.FIELD)
@Entity
@Table(name = "shaurma")
public class Shaurma {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Nationalized @Column(length = 256)
    @Size(min = 2, max = 256, message = " Shaurma name should between 2 and 256 symbols ")
    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "ingredient_has_shaurma",
        joinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "ingredient_id", referencedColumnName = "id") })
    @NotEmpty(message = " Ingredient set can not be EMPTY ")
    @Valid
    private Set<Ingredient> ingredientSet;

    public Shaurma() {
    }

    /*no-op*/
    public void setCost(Double cost) {
    }

    //TODO: Migrate to BigDecimal or Currency type
    @Access(AccessType.PROPERTY)
    @Column(name = "cost", precision = 7, scale = 2)
    @JsonGetter("cost")
    public Double getCost() {
        DoubleAdder doubleAdder = new DoubleAdder();

        //TODO ifNeeded to avoid unboxing + boxing as listed below
//        ingredientSet.parallelStream()
//                .forEach(ingredient -> doubleAdder.add(ingredient.getCost()));
//        return doubleAdder.doubleValue();

        ingredientSet.parallelStream()
            .mapToDouble(Ingredient::getCost)
            .forEach(doubleAdder::add);
        return doubleAdder.doubleValue();
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

    public Set<Ingredient> getIngredientSet() {
        return ingredientSet;
    }

    public void setIngredientSet(Set<Ingredient> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shaurma shaurma = (Shaurma) o;
        return  Objects.equals(this.id, shaurma.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
