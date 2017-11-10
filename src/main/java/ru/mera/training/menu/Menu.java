package ru.mera.training.menu;

import ru.mera.training.shaurma.Shaurma;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="menu")
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, updatable = false)
    private int id;
    @Column(name = "price")
    private float price;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myMenu")
    private List<Shaurma> shaurmas;

    public Menu() {

    }

    public List<Shaurma> getShaurmas() {
        hardcode();
        return shaurmas;
    }

    public void setShaurmas(List<Shaurma> shaurmas) {
        this.shaurmas = shaurmas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void hardcode(){
        /**HARDCODE*/
        Shaurma s1=new Shaurma();
        s1.setId(1);
        s1.setName("First");
        Shaurma s2=new Shaurma();
        s2.setId(2);
        s2.setName("Second");
        Shaurma s3=new Shaurma();
        s3.setId(3);
        s3.setName("Third");
        shaurmas.add(s1);
        shaurmas.add(s2);
        shaurmas.add(s3);
    }
}
//    public static void main(String[] args) {
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//        HibernateUtil hibernateUtil =(HibernateUtil)ctx.getBean("hibernateUtil");
//        Session session=hibernateUtil.getNewSession();
//        Transaction transaction=session.beginTransaction();
////            --------- add to table ---------
////        Order order=(Order)ctx.getBean("order");
////        order.setTotalCoast(100);
////        session.save(order);
////            --------- get from table and change in DB ---------
////        Ingredient ingredient= session.get(Ingredient.class,4);
////        ingredient.setName("pita");
//        transaction.commit();
//    }

