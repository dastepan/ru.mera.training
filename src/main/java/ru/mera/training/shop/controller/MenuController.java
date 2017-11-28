package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public List<Menu> getAllmenu() {
        return menuService.getAllMenu();
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Menu getMenuById(@PathVariable(value = "id") String id){
        return menuService.getMenuById(Integer.parseInt(id));
    }

//    @ResponseBody
//    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, , produces = "application/json;charset=utf-8")

}
