package ru.mera.sergeynazin.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(final String name) {
        super("Entity with ID " + name + " NOT found !");
    }

    public NotFoundException(final Long id) {
        super("Entity with ID " + id + " NOT found !");
    }

    public static NotFoundException throwNew(final String name) {
        return new NotFoundException(name);
    }

    public static NotFoundException throwNew(final Long id) {
        return new NotFoundException(id);
    }
}
