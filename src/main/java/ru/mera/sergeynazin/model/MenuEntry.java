package ru.mera.sergeynazin.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "menu")
public class MenuEntry {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    // TODO: Do I need @Column(columnDefinition = "string") ????
    //@MapsId // TODO: they say this one may cause problems or even just incorrect to use here. Didn't managed to explore
    //@JoinColumn(name = "shaurma_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn(name = "shaurma_id", referencedColumnName = "id")
    private Shaurma shaurma;

    //TODO: Migrate to BigDecimal or Currency type
    @Column(precision = 7, scale = 2, nullable = false)
    private Double price;

    public MenuEntry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Shaurma getShaurma() {
        return shaurma;
    }

    public void setShaurma(Shaurma shaurma) {
        this.shaurma = shaurma;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
