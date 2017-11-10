package ru.mera.training.order;


import ru.mera.training.shaurma.Shaurma;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name="orders")
public class Order {
    @Transient
    private AutoIncrementer autoIncrement;
    @Id
    @GeneratedValue
    private String orderNumber;
    private float totalCoast;
    @ManyToMany(mappedBy = "orders")
    private Set<Shaurma> shaurmasSet;


    public Order() {
    }

    public void init(){
        orderNumber=new Date(System.currentTimeMillis())+" ";
        orderNumber=orderNumber+ autoIncrement.getNumber();
    }

    public Set<Shaurma> getShaurmasSet() {
        return shaurmasSet;
    }
    public void setShaurmasSet(Set<Shaurma> shaurmasSet) {
        this.shaurmasSet = shaurmasSet;
    }
    public float getTotalCoast() {
        totalCoast=0;
            for(Shaurma s:shaurmasSet){
                totalCoast+=s.getCost();
            }
        return totalCoast;
    }
    public void setTotalCoast(float totalCoast) {
        this.totalCoast = totalCoast;
    }
    public AutoIncrementer getAutoIncrement() {
        return autoIncrement;
    }
    public void setAutoIncrement(AutoIncrementer autoIncrement) {
        this.autoIncrement = autoIncrement;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

}
