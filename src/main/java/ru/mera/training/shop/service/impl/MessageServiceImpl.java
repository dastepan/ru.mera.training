package ru.mera.training.shop.service.impl;

import org.springframework.stereotype.Service;
import ru.mera.training.shop.service.MessageService;

import javax.annotation.PostConstruct;

@Service("messageService")
public class MessageServiceImpl implements MessageService{

    public MessageServiceImpl() {
    }

    @Override
    public String getMessageInfo(String info) {
        return info;
    }
    @PostConstruct
    public void init(){

    }
}
