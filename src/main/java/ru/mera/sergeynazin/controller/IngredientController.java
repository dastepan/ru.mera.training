package ru.mera.sergeynazin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.service.IngredientService;

import javax.validation.Valid;
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
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredientInJSON(final Principal principal,
                                                                          @Valid @RequestBody final Ingredient transientEntity) {
        return CompletableFuture.completedFuture(createNew(transientEntity));
    }

    @Admin
    @Async
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createNewIngredientInXML(final Principal principal,
                                                                         @Valid @RequestBody final Ingredient transientEntity) {
        return CompletableFuture.completedFuture(createNew(transientEntity));
    }

    /**
     * Convenience method for shaurmamaker for to add new ingredient to db
     * @param ingredient body
     * @return 201 (Created) or 200 OK (for Caching at front) response
     *      containing a Location header field that provides an identifier
     *      for the primary resource created and a representation
     *      that describes the status of the request while referring
     *      to the new resource(s)
     *      OR throws
     *      422 {@link HttpStatus#UNPROCESSABLE_ENTITY}
     */
    private ResponseEntity<?> createNew(final Ingredient ingredient) {
        final Ingredient newOne = ingredientService.postOrThrow(ingredient);
        // TODO: MOCKED -> Delete
        /*final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/ingredient/{id}")
            .buildAndExpand(ingredientService.postOrThrow(ingredient)).toUri();*/

        final URI uri = URI.create("SAMPLE_PATH/"+newOne.getId());

        return ResponseEntity.created(uri).body(newOne);
    }
    // END_INCLUDE(IngredientController.POSTCreateNew)

    // BEGIN_INCLUDE(IngredientController.PUTUpdate)
    @Admin
    @Async
    @PutMapping(value = "update/{id}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> updateIngredientInJSON(final Principal principal,
                                                                       @PathVariable("id") final Long id,
                                                                       @Valid @RequestBody final Ingredient ingredient) {
        return CompletableFuture.completedFuture(updateEgMerge(id, ingredient));
    }

    @Admin
    @Async
    @PutMapping(value = "update/{id}", produces = { MediaType.APPLICATION_XML_VALUE } , consumes = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> updateIngredientInXML(final Principal principal,
                                                                      @PathVariable("id") final Long id,
                                                                      @Valid @RequestBody final Ingredient ingredient) {
        return CompletableFuture.completedFuture(updateEgMerge(id, ingredient));
    }

    /**
     * Updates the state of the Ingredient (presumably just cost)
     * @param id of the supplied entity
     * @param newDetached stateful entity with state to be supplied to database
     * @return 404 or 200
     */
    private ResponseEntity<?> updateEgMerge(final Long id, final Ingredient newDetached) {
        newDetached.setId(id);
        return ResponseEntity.ok(ingredientService.putOrThrow(newDetached));
    }
    // END_INCLUDE(IngredientController.PUTUpdate)


    // BEGIN_INCLUDE(IngredientController.DELETEFromDb)
    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInJSON(final Principal principal,
                                                                       @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> deleteIngredientInXML(final Principal principal,
                                                                      @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    private ResponseEntity<?> delete(final Long id) {
        ingredientService.deleteByIdOrThrow(id);
        return ResponseEntity.noContent().build();
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

    private ResponseEntity<?> get(final Long id) {
        return ResponseEntity.ok(ingredientService.getOrThrow(id));
    }
    // END_INCLUDE(IngredientController.GetById)


    // BEGIN_INCLUDE(IngredientController.GETAll)
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInJSON() {
        return CompletableFuture.completedFuture(getAll());
    }

    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Ingredient>>> getAllIngredientsInXML() {
        return CompletableFuture.completedFuture(getAll());
    }

    private ResponseEntity<Collection<Ingredient>> getAll() {
        return ResponseEntity.ok(ingredientService.getAll());
    }
    // END_INCLUDE(IngredientController.GETAll)
}
