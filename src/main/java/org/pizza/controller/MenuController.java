package org.pizza.controller;

import org.pizza.model.Menu;
import org.pizza.service.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuServiceImpl menuService;

    @GetMapping("/get/all")
    public List getAllMenu() {
        return menuService.getAll();
    }
    @GetMapping("/get/id/{id}")
    public Menu getMenuById(@PathVariable("id") String id) {
        return menuService.getById(Integer.parseInt(id));
    }
    @GetMapping("/get/name/{name}")
    public List getMenuByName(@PathVariable("name") String name) {
        return menuService.getByName(name);
    }
    @PutMapping("/add")
    public void addMenu(@RequestBody Menu entity) {
        menuService.add(entity);
    }
    @PostMapping("/update")
    public void updateMenu(@RequestBody Menu entity) {
        menuService.add(entity);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteMenu(@PathVariable("id") String id) {
        menuService.remove(Integer.parseInt(id));
    }
}