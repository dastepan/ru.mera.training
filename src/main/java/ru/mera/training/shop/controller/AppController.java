package ru.mera.training.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.mera.training.shop.model.CreateTable;
import ru.mera.training.shop.service.MessageService;

import javax.inject.Inject;
import java.util.Map;

@Controller                 // Объявить как контроллер
public class AppController {
    private final MessageService messageService; // транзакция
    private final CreateTable createTable;

    @Inject                 // Внедрить MessageServiceImpl
    public AppController(MessageService messageService, CreateTable createTable) {
        this.messageService = messageService;
        this.createTable = createTable;
    }

    @RequestMapping({"/","/home"}) // Обрабатывать запросы на получение главной страницы
    public String showHomePage(Model model) {
        model.addAttribute("shop", messageService.getMessageInfo("Welcome"));
        return "home";
    }

    @RequestMapping("/create")
    public String tableCreationInfo(Model model){
        model.addAttribute("status", createTable.createTableStatus());
        return "status";
    }
}
