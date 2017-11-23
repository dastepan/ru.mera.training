package ru.mera.sergeynazin.controller;

import org.hibernate.Session;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.Ingredient;
import ru.mera.sergeynazin.model.Order;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.IngredientService;
import ru.mera.sergeynazin.service.ShaurmaService;

import javax.validation.Valid;
import java.net.URI;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.SecureRandom;
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
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllInJSON(final Principal principal) {
        return CompletableFuture.completedFuture(getAll());
    }

    @Admin
    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllInXML(final Principal principal) {
        return CompletableFuture.completedFuture(getAll());
    }

    private ResponseEntity<Collection<Shaurma>> getAll() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    // END_INCLUDE(ShaurmaController.GETAll)


    // BEGIN_INCLUDE(ShaurmaController.POSTCreateNew)
    @Admin
    @Async
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> createInJSON(final Principal principal, @Valid @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(create(shaurma));
    }

    @Admin
    @Async
    @PostMapping(value = "/create", consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> createInXML(final Principal principal, @Valid @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(create(shaurma));
    }

    /**
     * Convenience Fancy method for fun
     * for shaurmamaker <strong>only</strong> for to add
     * new shaurma (if frontend would add functionality)
     */
    private ResponseEntity<Object> create(final Shaurma shaurma) {
        // TODO: MOCKED -> Delete
        /*final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("/shaurma/{id}")
            .buildAndExpand(shaurmaService.postOrThrow(shaurma)).toUri();*/

        final Shaurma body = shaurmaService.postOrThrow(shaurma);

        final URI uri = URI.create("SAMPLE_PATH/" + body.getId());

        return ResponseEntity.created(uri).body(body);
    }
    // END_INCLUDE(ShaurmaController.POSTCreateNew)


    // BEGIN_INCLUDE(ShaurmaController.PUTNewStateToDbEntity)
    @Admin
    @Async
    @PutMapping(value = "/update/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } , consumes = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrCreateInJSON(final Principal principal,
                                                                     @PathVariable("id") final Long id,
                                                                     @Valid @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreate(id, shaurma));
    }

    @Admin
    @Async
    @PutMapping(value = "/update/{id}", produces = { MediaType.APPLICATION_XML_VALUE } , consumes = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> updateOrCreateInXML(final Principal principal,
                                                                    @PathVariable("id") final Long id,
                                                                    @Valid @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(updateOrCreate(id, shaurma));
    }

    /**
     * проверяем сущ-ет ли шаурма с таким Id
     *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
     *      если нет, то создаём новую шаурму итерируя по ингредиентам
     *      @see Session#merge(Object)
     */
    @SuppressWarnings({"unchecked"})
    private ResponseEntity<?> updateOrCreate(final Long id, final Shaurma newDetached) {
        newDetached.setId(id);
        return shaurmaService.getOptionalIsExist(newDetached.getId())
            .map(shaurma -> {
                shaurmaService.putOrThrow(newDetached);
                return ResponseEntity.noContent().build();
            }).orElseGet(() -> create(newDetached));
    }
    // END_INCLUDE(ShaurmaController.PUTNewStateToDbEntity)


    // BEGIN_INCLUDE(ShaurmaController.DELETEFromDatabase)
    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteShaurmaInJSON(final Principal principal, @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteShaurmaInXML(final Principal principal, @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    private ResponseEntity<?> delete(final Long id) {
        shaurmaService.deleteByIdOrThrow(id);
        return ResponseEntity.noContent().build();
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
    private ResponseEntity<?> get(final Long id) {
        return ResponseEntity.ok(shaurmaService.getOrThrow(id));
    }
    // END_INCLUDE(ShaurmaController.GETById)


    // BEGIN_INCLUDE(ShaurmaController.GETAll)


    // BEGIN_INCLUDE(ShaurmaController.POSTCreateNew)
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInJSON(@Valid @RequestBody final Shaurma shaurma) throws NoSuchAlgorithmException {
        return CompletableFuture.completedFuture(add(shaurma));
    }

    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInXML(@Valid @RequestBody final Shaurma shaurma) throws NoSuchAlgorithmException {
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
    private ResponseEntity<?> add(final Shaurma shaurma) throws NoSuchAlgorithmException {
        ingredientService.validateExistsOrThrow(shaurma.getIngredientSet().toArray(new Ingredient[shaurma.getIngredientSet().size()]));
        final SecureRandom random = SecureRandom.getInstanceStrong();
        final byte[] values = new byte[20];
        random.nextBytes(values);
        final Long id = random.nextLong();
        shaurma.setId(id);
        currentOrder.getShaurmaList().add(shaurma);
        // TODO: MOCKED -> Delete
        /*final URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .replacePath("order/shaurma/{id}")
            .buildAndExpand(id).toUri();*/

        final URI uri = URI.create("SAMPLE_PATH/");

        // TODO: 11/21/17 getCost() needs to be serialized
        return ResponseEntity.created(uri).body(shaurma);
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
    private ResponseEntity<?> remove(final Long id) {
        final Shaurma shaurma = new Shaurma();
        shaurma.setId(id);
        if (currentOrder.getShaurmaList().remove(shaurma))
            return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }
    // END_INCLUDE(ShaurmaController.DELETEFromDatabase)
}
