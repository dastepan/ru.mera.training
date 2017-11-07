package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.service.MenuEntryService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@EnableAsync
@RestController
@RequestMapping("/menu")
public class MenuEntryController {

    private MenuEntryService menuEntryService;

    private ShaurmaService shaurmaService;

    public void setMenuEntryService(MenuEntryService menuEntryService) {
        this.menuEntryService = menuEntryService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @Async
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<MenuEntry>>> getAllMenuEntrysInJSON() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(menuEntryService.getAll()));
    }


    @Async
    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<Collection<MenuEntry>>> getAllMenuEntrysInXML() {
        return CompletableFuture.completedFuture(ResponseEntity.ok(menuEntryService.getAll()));
    }

    @Admin
    @Async
    @PostMapping(value = "/shaurma/add/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> addAsJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(add(id));
    }

    @Admin
    @Async
    @PostMapping(value = "/shaurma/add/{id}")
    public CompletableFuture<ResponseEntity<?>> addAsXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(add(id));
    }
    /**
     * Convenience method for shaurmamaker. Supposedly this will add predefined shaurma to menu
     * @param id shaurma unique id from db
     * @return 404 or 200 code with newly added to menu shaurma body todo maybe switch to MenuEntry body??
     */
    private ResponseEntity<?> add(final Long id) throws NotFoundException {
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity ::ok)
            .orElseThrow(() -> NotFoundException.throwNew(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteAsJSON(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteAsXML(@PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    /**
     * Convenience method for shaurmamaker. Supposedly this will delete predefined shaurma from menu
     * @param id MenuEntry id from db
     * @return 404 or 200 code with deleted from menu menuEntry body
     */
    private ResponseEntity<?> delete(final Long id) throws NotFoundException {
        return menuEntryService.optionalIsExist(id)
            .map(menuEntry -> {
                menuEntryService.delete(menuEntry);
                return ResponseEntity.ok(menuEntry);
            }).orElseThrow(() -> NotFoundException.throwNew(id));
    }

    /**
     * Helper methods
     * @param id identifier
     */
    // FIXME: There are methods in Hibernate API which looks up for entire DB by primary ke switch to them!
    private void checkOrThrowShaurma(final Long id) {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    private void checkOrThrowMenuEntry(final Long id) {
            menuEntryService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }
}
