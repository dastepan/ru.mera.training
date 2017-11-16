package org.pizza.controller;

import org.pizza.service.utilities.HibernateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private HibernateUtil hibernateUtil;


    @RequestMapping(value="/menu", method= RequestMethod.GET)
    @ResponseBody
    public List<String> getMenu(ModelMap model){
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