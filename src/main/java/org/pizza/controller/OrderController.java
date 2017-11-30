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
    public List getAllMenu() {
        return orderService.getAll();
    }
    @GetMapping("/get/id/{id}")
    public Order getMenuById(@PathVariable("id") String id) {
        return orderService.getById(Integer.parseInt(id));
    }
    @PutMapping("/add")
    public void addMenu(@RequestBody Order entity) {
        orderService.add(entity);
    }
    @PostMapping("/update")
    public void updateMenu(@RequestBody Order entity) {
        orderService.add(entity);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMenu(@PathVariable("id") String id) {
        orderService.remove(Integer.parseInt(id));
    }
}
