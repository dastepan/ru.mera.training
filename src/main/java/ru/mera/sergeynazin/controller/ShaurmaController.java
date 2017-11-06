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
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.repository.HibernateRepository;
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

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @Async
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    @Async
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<?>> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(get(id));
    }

    private ResponseEntity<?> get(final Long id) throws NotFoundException {
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Async
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllShaurmasInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }

    @Async
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<Collection<Shaurma>>> getAllShaurmasInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(shaurmaService.getAll()));
    }

    @Admin
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInJSON(final Principal principal, @RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    @Admin
    @Async
    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public CompletableFuture<ResponseEntity<?>> addInXML(final Principal principal, @RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    /**
     * Convenience method for shaurmamaker only for to add
     * new shaurma (if frontend would add functionality)
     */
    private CompletableFuture<ResponseEntity<?>> add(final Shaurma shaurma) {
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

    @Admin
    @Async
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> updateInJSON(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(update(id, shaurma));
    }

    @Admin
    @Async
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public CompletableFuture<ResponseEntity<?>> updateInXML(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return CompletableFuture.completedFuture(update(id, shaurma));
    }

    /**
     * проверяем сущ-ет ли шаурма с таким Id
     *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
     *      если нет, то создаём новую шаурму итерируя по ингредиентам
     *      @see Session#save(String,Object)
     */
    private ResponseEntity<?> update(final Long id, final Shaurma detached) {
        return shaurmaService.optionalIsExist(id)
            .map(persistentOldShaurma -> {
                detached.setId(id);
                /** TODO: Check if needs to be merged instead
                 * @see Session#merge(Object)
                 * @see HibernateRepository#mergeStateWithDbEntity(Object)
                 */
                shaurmaService.update(detached);
                return ResponseEntity.ok(detached);
            }).orElseGet(() -> {
                if (detached.getIngredientSet()
                    .parallelStream()
                    .allMatch(ingredient -> ingredientService.optionalIsExist(ingredient.getId()).isPresent())) {
                    return ResponseEntity.unprocessableEntity().body(detached);
                }
                shaurmaService.save(detached);
                return ResponseEntity.created(URI.create("/" + detached.getId())).body(detached);
            });
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<?>> deleteShaurmaInJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
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

    /**
     * Helper method
     * @param id identifier
     */
    private void checkOrThrow(final Long id) {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
