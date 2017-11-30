package org.pizza.controller;

import org.pizza.model.Order;
import org.pizza.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @GetMapping("/get/all")
    public List getAllOrder() {
        return orderService.getAll();
    }
    @GetMapping("/get/id/{id}")
    public Order getOrderById(@PathVariable("id") String id) {
        return orderService.getById(Integer.parseInt(id));
    }
    @PutMapping("/add")
    public void addOrder(@RequestBody Order entity) {
        orderService.add(entity);
    }
    @PostMapping("/update")
    public void updateOrder(@RequestBody Order entity) {
        orderService.add(entity);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable("id") String id) {
        orderService.remove(Integer.parseInt(id));
    }
}
