package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mera.training.shop.entity.Menu;
import ru.mera.training.shop.service.MenuService;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    public MenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Menu getMenuById(@PathVariable(value = "id") String id) {
        return menuService.getMenuById(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Menu> getMenuByName(@PathVariable(value = "name") String name) {
        return menuService.getMenuByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Menu addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Menu updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Menu deleteMenu(@PathVariable(value = "id") String id){
        return menuService.deleteMenu(Integer.parseInt(id));
    }
}
