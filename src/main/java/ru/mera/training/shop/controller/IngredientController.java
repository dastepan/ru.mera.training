package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.service.IngredientService;

import java.util.List;

// TODO: rename pathes for queries
@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    public IngredientService ingredientService;

    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")

    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    // localhost:8080/ingredient/get/  id number
    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")

    public Ingredient getIngredientById(@PathVariable(value = "id") String id) {
        return ingredientService.getIngredientById(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")

    public List<Ingredient> getIngredientsByName(@PathVariable(value = "name") String name) {
        return ingredientService.getIngredientsByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Ingredient updateIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.updateIngredient(ingredient);
    }

    // localhost:8080/ingredients/delete/5
    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Ingredient deleteIngredient(@PathVariable(value = "id") String id) {
        return ingredientService.deleteIngredient(Integer.parseInt(id));
    }
    //        return null;
    //    public Ingredient deleteIngredient(@RequestParam(value = "id") String id) {
    //    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    //    @ResponseBody
//    // localhost:8080/ingredients/delete?id=5

//    }
}
