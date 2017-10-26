package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    // TODO: Inject
    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping(produces = "application/json")
    public ResponseEntity<Collection<Ingredient>> getAllIngredientsInJSON() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    // TODO: 10/20/17 XML
    @GetMapping(produces = "application/xml")
    public ResponseEntity<List<Ingredient>> getAllIngredientsInXML() {
        return ResponseEntity.ok(ingredientService.getAll());
    }

    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:
    // FIXME:
    // FIXME:   Ничего не понятно с тремя методами ниже. Нужны разъясниея
    // FIXME:
    // FIXME:// FIXME:// FIXME:// FIXME:// FIXME:// FIXME:

    // FIXME: Does this one meant to be a CREATING and SAVE newly created ingredient by shaurmamaker???
    // TODO: 10/20/17 Aspect
    @PostMapping(value = "/ingredients/create/{ingredientName}", consumes = {"application/json" , "application/xml"})
    public ResponseEntity<?> createNew(@PathVariable("ingredientName") final String ingredientName, @RequestBody final Ingredient ingredient) {

        return ingredientService.optionalIsExist(ingredientName)
            .map(ResponseEntity.unprocessableEntity()::body)
            .orElseGet(() -> {
                ingredientService.save(ingredient);
                return ResponseEntity.noContent().build();
            });
    }

    @PostMapping(value = "/ingredients/add/{ingredientName}", consumes = {"application/json" , "application/xml"})
    public ResponseEntity<?> add(@PathVariable("ingredientName") final String ingredientName, @RequestBody final Ingredient ingredient) {

        checkOrThrowByName(ingredientName);

        return null; // см ювыше!
    }

    // TODO: 10/20/17 Aspect
    @DeleteMapping(value = "/ingredients/remove/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable("id") final Long id) {

        checkOrThrowById(id);

        return ingredientService.optionalIsExist(id)
            .map(ingredient -> {
                ingredientService.tryDelete(id);
                return ResponseEntity.ok(ingredient);
            }).orElse(ResponseEntity.notFound().build());
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowByName(final String name) {
        ingredientService.optionalIsExist(name)
            .orElseThrow(() -> new NotFoundExeption(name));
    }

    private void checkOrThrowById(final Long id) {
        ingredientService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
