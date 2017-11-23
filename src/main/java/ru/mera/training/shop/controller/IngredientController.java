package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.service.IngredientService;

import java.util.List;
// TODO: rename pathes for queries
@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    public IngredientService ingredientService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    // localhost:8080/ingredient/get/  id number
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Ingredient getIngredientById(@PathVariable(value = "id") String id) {
        return ingredientService.getIngredientById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Ingredient> getIngredientsByName(@PathVariable(value = "name") String name) {
        return ingredientService.getIngredientsByName(name);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Ingredient updateIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(ingredient);
    }

//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public Ingredient deleteIngredient(@RequestBody Ingredient ingredient) {
//        return ingredientService.deleteIngredient(ingredient);
//    }

    // TODO fix delete method
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Ingredient deleteIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.deleteIngredient(ingredient);
    }
}
