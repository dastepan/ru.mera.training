package org.pizza.controller;

import org.pizza.model.Ingredient;
import org.pizza.service.impl.IngredientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientServiceImpl ingredientService;

    @GetMapping("/get/all")
    public List getAllIngredients() {
        List<Ingredient> result = ingredientService.getAll();
        return result;
    }
    @GetMapping("/get/id/{id}")
    public Ingredient getIngredientById(@PathVariable("id") String id) {
        return ingredientService.getById(Integer.parseInt(id));
    }
    @GetMapping("/get/name/{name}")
    public List getIngredientsByName(@PathVariable("name") String name) {
        return ingredientService.getByName(name);
    }
    @PutMapping("/add")
    public void addIngredient(@RequestBody Ingredient entity) {
        ingredientService.add(entity);
    }
    @PostMapping("/update")
    public void updateIngredient(@RequestBody Ingredient entity) {
        ingredientService.add(entity);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteIngredient(@PathVariable(value = "id") String id) {
        ingredientService.remove(Integer.parseInt(id));
    }
}
