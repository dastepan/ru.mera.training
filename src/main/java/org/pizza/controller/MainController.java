package org.pizza.controller;

import org.pizza.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.pizza.hibernate.menu.Menu;


import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private HibernateUtil hibernateUtil;

    @RequestMapping(value="/menu", method= RequestMethod.GET)
    @ResponseBody
    public List<String> getMenu(ModelMap model){
        Session session= hibernateUtil.getNewSession();
        //1. При инициализации бина menu нужно из файла забрать инфу(должна переместиться в базу)
        //2. получить список из SQL и заменить хардкод на данные
        //hardcode

//        List<Pizza>hardcodeList=new ArrayList<>();
//        Pizza p1=(Pizza) context.getBean("pizza");
//        Pizza p2=(Pizza) context.getBean("pizza");
//        Pizza p3=(Pizza) context.getBean("pizza");
//        p1.setName("Best");
//        p2.setName("Yummy");
//        p3.setName("First");
//        hardcodeList.add(p1);
//        hardcodeList.add(p2);
//        hardcodeList.add(p3);
//        return hardcodeList;
        List<String> list=new ArrayList<>();
        list.add("one");list.add("two");list.add("three");
        return list;
    }
}

//                                              как отправить entity в БД
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