package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.controller.advice.Admin;
import ru.mera.sergeynazin.controller.advice.CreatingAlreadyExistentException;
import ru.mera.sergeynazin.controller.advice.NotFoundException;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.service.MenuEntryService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.net.URI;
import java.security.Principal;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

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
    public CompletableFuture<ResponseEntity<?>> addAsJSON(final Principal principal,
                                                          @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(add(id));
    }

    @Admin
    @Async
    @PostMapping(value = "/shaurma/add/{id}")
    public CompletableFuture<ResponseEntity<?>> addAsXML(final Principal principal,
                                                         @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(add(id));
    }
    /**
     * Convenience method for shaurmamaker. Supposedly this will add predefined shaurma to menu
     * @param id shaurma unique id from db
     * @return 404 or 200 code with newly added to menu shaurma body todo maybe switch to MenuEntry body??
     */
    private ResponseEntity<?> add(final Long id) {
        return shaurmaService.getOptionalIsExist(id)
            .map(shaurma ->
                menuEntryService.getOptionalIsExist(id)
                    .map((Function<MenuEntry, ResponseEntity<?>>) menuEntry -> {
                        throw new CreatingAlreadyExistentException(id, menuEntry);
                    }).orElseGet(() -> {
                        final MenuEntry entry = new MenuEntry();
                        entry.setShaurma(shaurma);
                        entry.setPrice(shaurma.getCost());
                        menuEntryService.save(entry);
                        // TODO: MOCKED -> Delete
                        /*final URI uri = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .replacePath("")
                            .build().toUri();*/

                        final URI uri = URI.create("SAMPLE_PATH/");

                        return ResponseEntity.created(uri).build();
                    })
            ).orElseThrow(() -> NotFoundException.getNew(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_JSON_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteAsJSON(final Principal principal,
                                                             @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    @Admin
    @Async
    @DeleteMapping(value = "/delete/{id}", produces = { MediaType.APPLICATION_XML_VALUE } )
    public CompletableFuture<ResponseEntity<?>> deleteAsXML(final Principal principal,
                                                            @PathVariable("id") final Long id) {
        return CompletableFuture.completedFuture(delete(id));
    }

    /**
     * Convenience method for shaurmamaker. Supposedly this will delete predefined shaurma from menu
     * @param id MenuEntry id from db
     * @return 404 or 200 code with deleted from menu menuEntry body
     */
    private ResponseEntity<?> delete(final Long id) {
        return menuEntryService.optionalIsExist(id)
            .map(menuEntry -> {
                menuEntryService.delete(menuEntry);
                return ResponseEntity.noContent().build();
            }).orElseThrow(() -> NotFoundException.getNew(id));
    }
}
