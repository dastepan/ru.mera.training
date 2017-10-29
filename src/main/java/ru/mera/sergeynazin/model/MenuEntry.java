package ru.mera.sergeynazin.model;

import javax.persistence.*;

@Entity
@Table(name = "menu")
public class MenuEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    // TODO: Do I need @Column(columnDefinition = "string") ????
    //@MapsId // TODO: they say this one may cause problems
    //@JoinColumn(name = "shaurma_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @PrimaryKeyJoinColumn(name = "shaurma_id", referencedColumnName = "id")
    private Shaurma shaurma;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2, nullable = false)
    private Double price;

    // TODO: 10/23/17 Do I need it empty constructor?
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
