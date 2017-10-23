package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.MenuEntry;
import ru.mera.sergeynazin.service.MenuEntryService;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.List;

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

    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
    public List<MenuEntry> getAllMenuEntrysInJSON() {
        return menuEntryService.getAll();
    }

    // TODO: 10/20/17 XML
    @RequestMapping(method = RequestMethod.GET, headers = "Accept=application/xml")
    public List<MenuEntry> getAllMenuEntrysInXML() {
        return menuEntryService.getAll();
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/shaurma/add/{id}",method = RequestMethod.POST)
    public ResponseEntity<?> add(@PathVariable("id") final Long id) {

        checkOrThrowShaurma(id);

        return shaurmaService.optionalIsExist(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {

        checkOrThrowMenuEntry(id);

        return menuEntryService.optionalIsExist(id)
            .map(body -> {
                menuEntryService.tryDelete(id);
                return ResponseEntity.ok(body);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // FIXME: There are methods in Hibernate API which looks up for entire DB by primary ke switch to them!
    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrowShaurma(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }

    private void checkOrThrowMenuEntry(final Long id) {
        menuEntryService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
