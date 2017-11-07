package ru.mera.sergeynazin.controller;

import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.IngredientService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    private IngredientService ingredientService;

    private Order currentOrder;

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

// BEGIN_INCLUDE(ShaurmaController.@Admin)
    // BEGIN_INCLUDE(ShaurmaController.GETAll)
    @Admin
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }

    @Admin
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }
    // END_INCLUDE(ShaurmaController.GETAll)


    // BEGIN_INCLUDE(ShaurmaController.POSTCreateNew)
    @Admin
    @Async
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> createInJSON(final Principal principal, @RequestBody final Shaurma shaurma) {
        return create(shaurma);
    }

    @Admin
    @Async
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createInXML(final Principal principal, @RequestBody final Shaurma shaurma) {
        return create(shaurma);
    }

    /**
     * Convenience method for shaurmamaker <strong>only</strong> for to add
     * new shaurma (if frontend would add functionality)
     */
    private CompletableFuture<ResponseEntity<?>> create(final Shaurma shaurma) {
        return CompletableFuture
            .supplyAsync(() -> {
                shaurmaService.save(shaurma);
                return shaurma.getId();
            }).thenApply(id ->
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(id).toUri())
            .thenComposeAsync(created ->
                CompletableFuture
                    .completedFuture(ResponseEntity.created(created).build()));
    }
    // END_INCLUDE(ShaurmaController.POSTCreateNew)


    // BEGIN_INCLUDE(ShaurmaController.PUTNewStateToDbEntity)
    @Admin
    @Async
    @PutMapping(value = "/update/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } , consumes = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrCreateInJSON(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreate(id, shaurma));
    }

    @Admin
    @Async
    @PutMapping(value = "/update/{id}", produces = { MediaType.APPLICATION_XML_VALUE } , consumes = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrCreateInXML(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreate(id, shaurma));
    }

    /**
     * проверяем сущ-ет ли шаурма с таким Id
     *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
     *      если нет, то создаём новую шаурму итерируя по ингредиентам
     *      @see Session#save(Object)
     */
    private ResponseEntity<?> updateOrCreate(final Long id, final Shaurma newDetached) {
         return shaurmaService.optionalIsExist(id)
            .map(oldPersistent -> {
                newDetached.setId(id);
                return ResponseEntity.ok(shaurmaService.merge(newDetached));
            }).orElseGet(() -> {
             if (newDetached.getIngredientSet()
                 .parallelStream()
                 .allMatch(ingredient -> ingredientService.optionalIsExist(ingredient.getId()).isPresent())) {
                 return ResponseEntity.unprocessableEntity().body(newDetached);
             }
             shaurmaService.save(newDetached);
             final URI created = ServletUriComponentsBuilder
                 .fromCurrentRequest()
                 .replacePath("/{id}")
                 .buildAndExpand(newDetached.getId()).toUri();
             return ResponseEntity.created(created).body(newDetached);
         });
    }
    // END_INCLUDE(ShaurmaController.PUTNewStateToDbEntity)


    // BEGIN_INCLUDE(ShaurmaController.DELETEFromDatabase)
    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteShaurmaInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteShaurmaInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    private ResponseEntity<?> delete(final Long id) throws NotFoundException {
        return shaurmaService.optionalIsExist(id)
            .map(shaurma -> {
                shaurmaService.delete(shaurma);
                return ResponseEntity.ok(shaurma);
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }
    // END_INCLUDE(ShaurmaController.DELETEFromDatabase)
// END_INCLUDE(ShaurmaController.@Admin)



    // BEGIN_INCLUDE(ShaurmaController.GETById)
    @Async
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    @Async
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    /**
     * Gets shaurma from DB to display
     * @param id shaurma id from db (from displayed Menu)
     * @return 404 or 200
     * @throws NotFoundException resource not found (e.g.404)
     */
    private ResponseEntity<?> get(final Long id) throws NotFoundException {
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }
    // END_INCLUDE(ShaurmaController.GETById)


    // BEGIN_INCLUDE(ShaurmaController.GETAll)

    /**
     * List all from Database
     * @return all
     */
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllShaurmasInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }

    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllShaurmasInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }
    // END_INCLUDE(ShaurmaController.GETAll)


    // BEGIN_INCLUDE(ShaurmaController.POSTCreateNew)
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInJSON(@RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(add(shaurma));
    }

    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInXML(@RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(add(shaurma));
    }

    // TODO: 11/7/17 Add validators to all POJOs or else to validate DB entityes for current situation, e.g. should contain certain ingredients
    /**
     * Add picked from menu or otherwise constructed {@link Shaurma} to {@link #currentOrder}
     * If the entity is detached it should be further persisted when {@link #currentOrder} is to be saved
     * If the entity is constructed and therefor transient needs to be saved
     * @param shaurma an entity
     * @return 200
     */
    private ResponseEntity<?> add(final Shaurma shaurma) {
        currentOrder.getShaurmaSet().add(shaurma);
        return ResponseEntity.ok(shaurma);
    }
    // END_INCLUDE(ShaurmaController.POSTCreateNew)


    // BEGIN_INCLUDE(ShaurmaController.DELETEFromDatabase)
    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> removeShaurmaInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(remove(id));
    }

    @Async
    @DeleteMapping(value = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> removeShaurmaInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(remove(id));
    }

    /**
     * Removes shaurma from current order
     * @param id id
     * @return 200
     * @throws NotFoundException 404
     */
    private ResponseEntity<?> remove(final Long id) throws NotFoundException {
        currentOrder.getShaurmaSet()
            .parallelStream()
            .forEach(shaurma -> {
                if (id.equals(shaurma.getId())) {
                    currentOrder.getShaurmaSet().remove(shaurma);
                }
            });
        return ResponseEntity.noContent().build();
    }
    // END_INCLUDE(ShaurmaController.DELETEFromDatabase)

    /**
     * Helper method
     * @param id identifier
     */
    private void checkOrThrow(final Long id) {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
