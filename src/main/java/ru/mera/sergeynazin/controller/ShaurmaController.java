package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return get(id);
    }

    // TODO: 10/20/17 XML
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return get(id);
    }

    private ResponseEntity<?> get(final Long id) {
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInJSON() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInXML() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> addInJSON(@RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> addInXML(@RequestBody final Shaurma shaurma) {
        return add(shaurma);
    }

    /**
     * Convenience method for shaurmamaker only for to add
     * new shaurma (if frontend would add functionality)
     */
    // TODO: 10/20/17 Aspect
    // todo: maybe send the full URI with HttpRequest
    private ResponseEntity<?> add(final Shaurma shaurma) {
        shaurmaService.save(shaurma);
        return ResponseEntity.created(URI.create("/" + shaurma.getId())).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateInJson(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return update(id, shaurma);
    }
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> updateInXML(@PathVariable("id") final Long id, @RequestBody final Shaurma shaurma) {
        return update(id, shaurma);
    }

    /**
     * проверяем сущ-ет ли шаурма с таким Id
     *      есди да то новый стейт из объекта копируем в старый и возвращаем новую шаурму
     *      если нет, то создаём новую шаурму итерируя по ингредиентам
     */
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> update(final Long id, final Shaurma detached) {
        return shaurmaService.optionalIsExist(id)
            .map(persistentOldShaurma -> {
                detached.setId(id);
                shaurmaService.update(detached);
                return ResponseEntity.ok(detached);
            }).orElseGet(() -> {
                shaurmaService.save(detached);
                // FIXME: Iterate through ingredients
                return ResponseEntity.created(URI.create("/" + detached.getId())).body(detached);
            });
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteShaurmaInJson(@PathVariable("id") final Long id) {
        return delete(id);
    }
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> deleteShaurmaInXML(@PathVariable("id") final Long id) {
        return delete(id);
    }
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(final Long id) {
        return shaurmaService.optionalIsExist(id)
            .map(shaurma -> {
                shaurmaService.delete(shaurma);
                return ResponseEntity.ok(shaurma);
            }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Helper method
     * @param id identifier
     */
    // TODO: 10/23/17 WHY "THE RESULT OF orElseThrough() is IGNORED" ???(...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrow(final Long id) {
        try {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
        } catch (NotFoundExeption notFoundExeption) {
            notFoundExeption.printStackTrace();
        }
    }
}
