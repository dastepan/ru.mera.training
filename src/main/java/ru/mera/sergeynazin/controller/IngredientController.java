package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    // TODO: Inject
    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Ingredient> getAllIngredientsInJSON() {
        return ingredientService.getAll();
    }

    // TODO: 10/20/17 XML
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/xml")
    public List<Ingredient> getAllIngredientsInXML() {
        return ingredientService.getAll();
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/ingredients/add/{ingredientName}", method = RequestMethod.POST)
    public ResponseEntity<?> add(@PathVariable("ingredientName") final String ingredientName, @RequestBody final Ingredient ingredient) {

        checkOrThrowByName(ingredientName);

        return ingredientService.optionalIsExist(ingredientName)
            .map(ResponseEntity.unprocessableEntity()::body)
            .orElseGet(() -> {
                ingredientService.save(ingredient);
                return ResponseEntity.ok(ingredient);
            });
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/ingredients/remove/{id}",method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteIngredientInJson(@PathVariable("id") final Long id) {
        checkOrThrowById(id);
        ingredientService.tryDelete(id);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/ingredients/remove/{id}",method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public void deleteIngredientInXML(@PathVariable("id") final Long id) {
        checkOrThrowById(id);
        ingredientService.tryDelete(id);
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowByName(final String name) {
        ingredientService.optionalIsExist(name)
            .orElseThrow(() -> new NotFoundExection(name));
    }

    private void checkOrThrowById(final Long id) {
        ingredientService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExection(String.valueOf(id)));
    }
}
