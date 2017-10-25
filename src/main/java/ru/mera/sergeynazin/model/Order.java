package ru.mera.sergeynazin.model;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Table(name = "order")
public class Order {

    @Id


    @Generated(GenerationTime.INSERT)
    @Column(        // TODO: Insertable seems to be redundant
        length = 32,
        unique = true,
        nullable = false,
        updatable = false,
        columnDefinition = "AS CONCAT( COALESCE(order_number.id, ' ', order_number.date)"
    )
    @Type(type = "string")
    @Convert(converter = OrderNumberConverter.class)
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private OrderNumber orderNumber;

    @org.hibernate.annotations.Type(type = "big_decimal")
    @Column(precision = 7, scale = 2)
    private Double totalCost;


    // TODO: TypeConverter from String to int
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "order_has_shaurma",
        joinColumns = { @JoinColumn(name = "order_orderNumber", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "shaurma_id", referencedColumnName = "id") }
    )
    private Set<Shaurma> shaurmaSet;

    // TODO: 10/23/17 Do I need it empty constructor?
    public Order() {
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(OrderNumber orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Set<Shaurma> getShaurmaSet() {
        return shaurmaSet;
    }

    public void setShaurmaSet(Set<Shaurma> shaurmaSet) {
        this.shaurmaSet = shaurmaSet;
    }

    @Entity
    @Table(name = "order_number")
    public static class OrderNumber  {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", unique = true, nullable = false, updatable = false)
        private Long id;

        @Column(
            columnDefinition = "NOT NULL DEFAULT TO_CHAR(CURRENT_TIMESTAMP,'YYYY-MM-DD')",
            name = "date",
            nullable = false,
            updatable = false
        )
        private LocalDate localDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public void setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
        }
    }

    @Converter
    public static class OrderNumberConverter implements AttributeConverter<OrderNumber, String> {

        @Override
        public String convertToDatabaseColumn(OrderNumber attribute) {
            return String.valueOf(attribute.getLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                + "_" + attribute.getId();
        }

        @Override
        public OrderNumber convertToEntityAttribute(String dbData) {
            final OrderNumber orderNumber = new OrderNumber();
            orderNumber
                .setLocalDate(
                    LocalDate.parse(dbData.substring(0,9),DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                );
            orderNumber.setId(Long.valueOf(dbData.substring(11)));
            return orderNumber;
        }
    }

}
