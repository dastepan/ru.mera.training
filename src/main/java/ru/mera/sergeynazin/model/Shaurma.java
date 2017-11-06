package ru.mera.sergeynazin.model;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.DoubleAdder;

@Entity
@Table(name = "shaurma")
public class Shaurma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(length = 256)
    @Nationalized
    private String name;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "ingredient_has_shaurma",
        joinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "ingredient_id", referencedColumnName = "id") }
    )
    private Set<Ingredient> ingredientSet;

    // TODO: 10/23/17 Do I need it empty constructor?
    public Shaurma() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Shaurma shaurma = (Shaurma) o;

        return Objects.equals(this.name, shaurma.name)
            && Objects.equals(this.id, shaurma.id);
    }

    /**
     * For educational purposes this is done the old-school way
     * rather than could be performed in just one of code
     * btw, this is what pretty much {@link Objects#hash(Object...)} is doing...
     * @see Ingredient#hashCode()
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
