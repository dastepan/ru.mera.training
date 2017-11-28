package org.pizza.controller;


import org.pizza.model.Menu;
import org.pizza.model.Pizza;
import org.pizza.service.impl.MenuServiceImpl;
import org.pizza.service.utilities.Cntx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private MenuServiceImpl menuService;

    @RequestMapping(value="/menu", method= RequestMethod.GET)
    @ResponseBody
    public List<Pizza> getMenu(){
        List<Menu> list=menuService.getAll();
        return list.get(0).getPizzas();
    }


}
