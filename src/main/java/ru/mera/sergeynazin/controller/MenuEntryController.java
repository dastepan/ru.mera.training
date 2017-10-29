package ru.mera.sergeynazin.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.service.MenuEntryService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.Collection;

@RestController
@RequestMapping("/menu")
public class MenuEntryController {

    // TODO: Inject
    private MenuEntryService menuEntryService;
    // TODO: Inject
    private ShaurmaService shaurmaService;

    public void setMenuEntryService(MenuEntryService menuEntryService) {
        this.menuEntryService = menuEntryService;
    }

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<MenuEntry>> getAllMenuEntrysInJSON() {
        return ResponseEntity.ok(menuEntryService.getAll());
    }

    // TODO: 10/20/17 XML
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Collection<MenuEntry>> getAllMenuEntrysInXML() {
        return ResponseEntity.ok(menuEntryService.getAll());
    }

    @PostMapping(value = "/shaurma/add/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addAsJSON(@PathVariable("id") final Long id) {
        return add(id);
    }

    @PostMapping(value = "/shaurma/add/{id}")
    public ResponseEntity<?> addAsXML(@PathVariable("id") final Long id) {
        return add(id);
    }
    /**
     * Convenience method for shaurmamaker. Supposedly this will add predefined shaurma to menu
     * @param id shaurma unique id from db
     * @return error on Shaurma not found or 200 code with newly added to menu shaurma body todo maybe switch to MenuEntry body??
     */
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> add(final Long id) {
        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity ::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAsJSON(@PathVariable("id") final Long id) {
        return delete(id);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> deleteAsXML(@PathVariable("id") final Long id) {
        return delete(id);
    }

    /**
     * Convenience method for shaurmamaker. Supposedly this will delete predefined shaurma from menu
     * @param id MenuEntry id from db
     * @return 404 or 200 code with deleted from menu menuEntry body
     */
    // TODO: 10/20/17 Aspect
    private ResponseEntity<?> delete(Long id) {
        return menuEntryService.optionalIsExist(id)
            .map(menuEntry -> {
                menuEntryService.delete(menuEntry);
                return ResponseEntity.ok(menuEntry);
            }).orElse(ResponseEntity.notFound().build());
    }


    // FIXME: There are methods in Hibernate API which looks up for entire DB by primary ke switch to them!
    // TODO: 10/23/17 WHY "THE RESULT OF orElseThrough() is IGNORED" ???(...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowShaurma(final Long id) {
        try {
            shaurmaService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
        } catch (NotFoundExeption notFoundExeption) {
            notFoundExeption.printStackTrace();
        }
    }

    private void checkOrThrowMenuEntry(final Long id) {
        try {
            menuEntryService.optionalIsExist(id)
                .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
        } catch (NotFoundExeption notFoundExeption) {
            notFoundExeption.printStackTrace();
        }
    }
}
