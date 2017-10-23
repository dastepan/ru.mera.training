package ru.mera.sergeynazin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mera.sergeynazin.model.Shaurma;
import ru.mera.sergeynazin.service.ShaurmaService;

import java.util.List;

@RestController
@RequestMapping("/shaurma")
public class ShaurmaController {

    private ShaurmaService shaurmaService;

    public void setShaurmaService(ShaurmaService shaurmaService) {
        this.shaurmaService = shaurmaService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public Shaurma getShaurmaByIdInJSON(@PathVariable("id") final Long id) {
        checkOrThrow(id);
        return shaurmaService.loadAsPersistent(id);
    }

    // TODO: 10/20/17 XML
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, headers = "Accept=application/xml")
    public Shaurma getShaurmaByIdInXML(@PathVariable("id") final Long id) {
        checkOrThrow(id);
        return shaurmaService.loadAsPersistent(id);
    }
    // TODO: Do I need value = "/" ???
    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Shaurma> getAllShaurmasInJSON() {
        return shaurmaService.getAll();
    }

    // TODO: 10/20/17 XML
    // TODO: Do I need value = "/" ???
    @RequestMapping(value = "/", method = RequestMethod.GET, headers = "Accept=application/xml")
    public List<Shaurma> getAllShaurmasInXML() {
        return shaurmaService.getAll();
    }


    /**
     * Convenience metod for shaurmamaker only for to add new shaurma (if frontend would add functionality)
     */
    // TODO: 10/20/17 Aspect
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody final Shaurma shaurma) {
        shaurmaService.save(shaurma);
        return ResponseEntity.ok(shaurma);
    }

    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/json")
    public void updateShaurmaInJson(@RequestBody final Shaurma shaurma) {
        checkOrThrow(shaurma.getId());
        shaurmaService.update(shaurma);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/xml")
    public void updateShaurmaInXML(@RequestBody final Shaurma shaurma) {
        checkOrThrow(shaurma.getId());
        shaurmaService.update(shaurma);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void deleteShaurmaInJson(@PathVariable("id") final Long id) {
        shaurmaService.tryDelete(id);
    }
    // TODO: 10/20/17 Aspect
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, headers = "Accept=application/xml")
    public void deleteShaurmaInXML(@PathVariable("id") final Long id) {
        shaurmaService.tryDelete(id);
    }

    // TODO: 10/23/17 WHY IGNORED ??? (...- No Handler ?? )witch to security with (also there is Principal)
    private void checkOrThrow(final Long id) {
        shaurmaService.optionalIsExist(id)
            .orElseThrow(() -> new NotFoundExeption(String.valueOf(id)));
    }
}
