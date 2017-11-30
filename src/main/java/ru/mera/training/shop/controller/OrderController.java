package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Order getOrderById(@PathVariable(value = "id") String id) {
        return orderService.getOrderById(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Order addOrder(@RequestBody Order order) {
        return orderService.addOrder(order);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Order updateOrder(@RequestBody Order order) {
        return orderService.updateOrder(order);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Order deleteOrder(@PathVariable(value = "id") String id) {
        return orderService.deleteOrder(Integer.parseInt(id));
    }
}

