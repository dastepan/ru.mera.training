package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import java.net.URI;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Convenience method for shaurmamaker for to add new ingredient to db
     * @param ingredientName ...
     * @param ingredient xml body
     * @return 201 (Created) or 200 OK (for Caching at front) response
     * containing a Location header field that provides an identifier
     * for the primary resource created and a representation
     * that describes the status of the request while referring
     * to the new resource(s)
     */
    @Admin
    @Async
    @PostMapping(value = "/create/{ingredient_name}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredient(@PathVariable("ingredient_name") final String ingredientName,
                                                                    @RequestBody final Ingredient ingredient) {
        if (ingredient.getId() != null)
            return CompletableFuture.completedFuture(ResponseEntity.unprocessableEntity().body(ingredient));
        return CompletableFuture.completedFuture(
            ingredientService.optionalIsExist(ingredientName)
            .map(i -> ResponseEntity.unprocessableEntity().body(ingredient))
            .orElseGet(() -> {
                ingredient.setName(ingredientName);
                ingredientService.save(ingredient);
                final URI created = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .replacePath("/{id}")
                    .buildAndExpand(ingredient.getId()).toUri();
                return ResponseEntity.created(created).body(ingredient);
            }));
    }

    @Async
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> getIngredientByIdInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }


    @Async
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<?>> getIngredientByIdInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    private ResponseEntity<?> get(final Long id) throws NotFoundException {
        return ingredientService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Async
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }


    @Async
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/remove/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/remove/{id}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    private ResponseEntity<?> delete(final Long id) throws NotFoundException {
        return ingredientService.optionalIsExist(id)
            .map(ingredient -> {
                ingredientService.delete(ingredient);
                return ResponseEntity.ok(ingredient);
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }

    /**
     * Helper methods
     * @param name/id identifier
     */
    private void checkOrThrowByName(final String name) {
            ingredientService.optionalIsExist(name)
                .orElseThrow(() -> new NotFoundException(name));
    }

    private void checkOrThrowById(final Long id) {
            ingredientService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
