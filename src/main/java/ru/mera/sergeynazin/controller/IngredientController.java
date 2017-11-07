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
import java.security.Principal;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

// BEGIN_INCLUDE(IngredientController.@Admin)
    // BEGIN_INCLUDE(IngredientController.POSTCreateNew)

    @Admin
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredientInJSON(final Principal principal,
                                                                          @RequestBody final Ingredient transientEntity) {
        return CompletableFuture.completedFuture(createNew(transientEntity));
    }

    @Admin
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredientInXML(final Principal principal,
                                                                         @RequestBody final Ingredient transientEntity) {
        return CompletableFuture.completedFuture(createNew(transientEntity));
    }

    /**
     * Convenience method for shaurmamaker for to add new ingredient to db
     * @param ingredient body
     * @return 201 (Created) or 200 OK (for Caching at front) response
     * containing a Location header field that provides an identifier
     * for the primary resource created and a representation
     * that describes the status of the request while referring
     * to the new resource(s)
     */
    private ResponseEntity<?> createNew(final Ingredient ingredient) {
        ingredientService.save(ingredient);
        final URI created = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(ingredient.getId()).toUri();
        return ResponseEntity.created(created).body(ingredient);
    }
    // END_INCLUDE(IngredientController.POSTCreateNew)

    // BEGIN_INCLUDE(IngredientController.PUTUpdate)
    @Admin
    @Async
    @PutMapping(value = "update/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> updateIngredientInJSON(@PathVariable("id") final Long id,
                                                                       @RequestBody final Ingredient ingredient) {
        return CompletableFuture.completedFuture(updateEgMerge(id, ingredient));
    }

    @Admin
    @Async
    @PutMapping(value = "update/{id}", produces = { MediaType.APPLICATION_XML_VALUE } , consumes = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> updateIngredientInXML(@PathVariable("id") final Long id,
                                                                      @RequestBody final Ingredient ingredient) {
        return CompletableFuture.completedFuture(updateEgMerge(id, ingredient));
    }

    /**
     * Updates the state of the Ingredient (presumably just cost)
     * @param id of the supplied entity
     * @param newDetached stateful entity with state to be supplied to database
     * @return 404 or 200
     * @throws NotFoundException 404
     */
    private ResponseEntity<?> updateEgMerge(final Long id, final Ingredient newDetached) throws NotFoundException {
        return ingredientService.optionalIsExist(id)
            .map(oldPersistent -> {
                newDetached.setId(id);
                return ResponseEntity.ok(ingredientService.merge(newDetached));
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }
    // END_INCLUDE(IngredientController.PUTUpdate)


    // BEGIN_INCLUDE(IngredientController.DELETEFromDb)
    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
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
    // END_INCLUDE(IngredientController.DELETEFromDb)
// END_INCLUDE(IngredientController.@Admin)



    // BEGIN_INCLUDE(IngredientController.GETById)
    @Async
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getIngredientByIdInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }


    @Async
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getIngredientByIdInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    private ResponseEntity<?> get(final Long id) throws NotFoundException {
        return ingredientService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }
    // END_INCLUDE(IngredientController.GetById)


    // BEGIN_INCLUDE(IngredientController.GETAll)
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }

    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(ingredientService.getAll()));
    }
    // END_INCLUDE(IngredientController.GETAll)

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
