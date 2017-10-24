package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.Collection;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        return getShaurmaById(id);
    }

    // TODO: 10/20/17 XML
    @GetMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        return getShaurmaById(id);
    }

    private ResponseEntity<?> getShaurmaById(final Long id) {
        checkOrThrow(id);
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }


    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = "application/json")
    @RequestMapping(value = "/", method = GET, headers = "Accept=application/json")
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInJSON() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    @GetMapping(value = "/", produces = "application/xml")
    public ResponseEntity<Collection<Shaurma>> getAllShaurmasInXML() {
        return ResponseEntity.ok(shaurmaService.getAll());
    }

    /**
     * Convenience method for shaurmamaker only for to add new shaurma (if frontend would add functionality)
     */
    // TODO: 10/20/17 Aspect
    @RequestMapping(method = POST)
    public ResponseEntity<?> add(@RequestBody final Shaurma shaurma) {
        shaurmaService.save(shaurma);
        return ResponseEntity.ok(shaurma);
    }


    @PutMapping(value = "/{id}", produces = "application/json")
    @RequestMapping(value = "/{id}", method = PUT, headers = "Accept=application/json")
    public ResponseEntity<?> updateShaurmaInJson(@RequestBody final Shaurma shaurma) {
        return updateShaurma(shaurma);
    }
    @PutMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> updateShaurmaInXML(@RequestBody final Shaurma shaurma) {
        return updateShaurma(shaurma);
    }
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> updateShaurma(Shaurma shaurma) {

        checkOrThrow(shaurma.getId());

        return shaurmaService.optionalIsExist(shaurma.getId())
            .map(shaurma1 -> {
                shaurmaService.update(shaurma);
                return ResponseEntity.ok(shaurma);
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> deleteShaurmaInJson(@PathVariable("id") final Long id) {
        return delete(id);
    }
    @DeleteMapping(value = "/{id}", produces = "application/xml")
    public ResponseEntity<?> deleteShaurmaInXML(@PathVariable("id") final Long id) {
        return delete(id);
    }
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(Long id) {

        checkOrThrow(id);

        return shaurmaService.optionalIsExist(id)
            .map(order -> {
                shaurmaService.tryDelete(id);
                return ResponseEntity.ok(order);
            }).orElse(ResponseEntity.notFound().build());
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrow(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
