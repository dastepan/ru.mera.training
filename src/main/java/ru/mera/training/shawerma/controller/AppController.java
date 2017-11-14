package ru.mera.training.shawerma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.util.Map;

@Controller                 // Объявить как контроллер
public class AppController {

  //  private ShawermaServise shawermaServise; // транзакция

//    @Inject                 // Внедрить ShawermaService
//    public AppController(ShawermaServise shawermaServise) {
//        this.shawermaServise = shawermaServise;
//    }

    @RequestMapping({"/","/home"}) // Обрабатывать запросы на получение главной страницы
    public String showHomePage(Map<String, Object> model) {
//        model.put("shawerma", shawermaServise.getRecentShawerma());
        return "home";
    }
}
