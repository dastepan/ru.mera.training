package org.pizza.controller;

import org.pizza.model.Pizza;
import org.pizza.service.impl.PizzaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class PizzaController {
    @Autowired
    private PizzaServiceImpl pizzaService;

    @GetMapping("/get/all")
    public List getAllMenu() {
        return pizzaService.getAll();
    }
    @GetMapping("/get/id/{id}")
    public Pizza getMenuById(@PathVariable("id") String id) {
        return pizzaService.getById(Integer.parseInt(id));
    }
    @PutMapping("/add")
    public void addMenu(@RequestBody Pizza entity) {
        pizzaService.add(entity);
    }
    @PostMapping("/update")
    public void updateMenu(@RequestBody Pizza entity) {
        pizzaService.add(entity);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMenu(@PathVariable("id") String id) {
        pizzaService.remove(Integer.parseInt(id));
    }
}