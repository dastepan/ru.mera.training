package ru.mera.sergeynazin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExeption extends RuntimeException {
    public NotFoundExeption(String nameOrOtherId) {
        super("Entity with ID " + nameOrOtherId + " NOT found !");
    }
}
