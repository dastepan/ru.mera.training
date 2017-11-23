package ru.mera.sergeynazin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    @Nationalized @Column(length = 45, unique = true, nullable = false)
    @Size(min = 2, max = 45, message = " Ingredient name should between 2 and 45 symbols ")
    private String name;

    //TODO: Migrate to BigDecimal or Currency type
    @Column(precision = 7, scale = 2)
    @NotNull(message = " Ingredient cost should not be NULL ")
    @Positive(message = " Cost of Ingredient should be between 0.00 and 99,999.99 ")
    @DecimalMax(value = "99999.99", message = " Decimal cost of Ingredient should not be more than 99,999.99 ") // TODO: 11/7/17 delete
    @DecimalMin(value = "1.00", message = " Decimal cost of Ingredient should not be less than 1.00 ") // TODO: 11/7/17 delete
    private Double cost;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(this.name, ingredient.name)
            && Objects.equals(this.id, ingredient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.id);
    }

}
