package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mera.training.shop.entity.Order;
import ru.mera.training.shop.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    public OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}" , method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Order getOrderById(@PathVariable(value = "id") String id){
        return orderService.getOrderById(id);
    }
}
