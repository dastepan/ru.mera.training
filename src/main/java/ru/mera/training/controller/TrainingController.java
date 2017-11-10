package ru.mera.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mera.training.menu.Menu;
import ru.mera.training.shaurma.Shaurma;


import java.util.ArrayList;
import java.util.List;

@Controller
public class TrainingController {
    @Autowired
    Menu menu;
    @RequestMapping(value="/menu", method= RequestMethod.GET)
    @ResponseBody
    public List<String> getMenu(ModelMap model){
        List<String>result=new ArrayList<>();
        result.add("first");
        result.add("second");
        result.add("third");
        result.add("fourth");
        return result;
    }
}
