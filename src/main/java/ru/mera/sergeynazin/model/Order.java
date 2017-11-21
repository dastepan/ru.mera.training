package ru.mera.sergeynazin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "order_1")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;

    /**
     * Even though the queries by natural Id is not used in service
     * implementations here, this could become a staring point for that
     */
    @NaturalId @Column(name = "order_number", length = 32, unique = true, nullable = false, updatable = false)
    //@Formula(value = "(CONCAT_WS('_', TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD'), SUM( 1, (SELECT MAX(id) FROM order_1 WHERE id LIKE CONCAT(TO_CHAR(CURRENT_DATE, 'YYYY-MM-DD'), '%')))))")
    //@Formula(value = "TO_CHAR(GREATEST(1,3))")
    private String orderNumber;

    //TODO: Migrate to BigDecimal or Currency type
    @Column(precision = 7, scale = 2)
    private Double totalCost;


    // TODO: TypeConverter from String to int
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "order_has_shaurma",
        joinColumns = { @JoinColumn(name = "order_order_number", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") })
    @NotEmpty(message = " Shaurma list can not be EMPTY ")
    @Valid
    private List<Shaurma> shaurmaList;


    public Order() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Shaurma> getShaurmaList() {
        return shaurmaList;
    }

    public void setShaurmaList(List<Shaurma> shaurmaList) {
        this.shaurmaList = shaurmaList;
    }




}
