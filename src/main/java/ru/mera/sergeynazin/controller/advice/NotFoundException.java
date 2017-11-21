package ru.mera.sergeynazin.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.Arrays;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {


    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException(final String name) {
        super("Entity with name " + name + " NOT found !");
    }

    public NotFoundException(final String... name) {
        super("Entities with names " + Arrays.toString(name) + " NOT found !");
    }

    public NotFoundException(final Serializable id) {
        super("Entity with id " + id + " NOT found !");
    }

    public NotFoundException(final Serializable... id) {
        super("Entities with id-s " + Arrays.toString(id) + " NOT found !");
    }

    public static NotFoundException getNew(final String name) {
        return new NotFoundException(name);
    }

    public static NotFoundException getNew(final Serializable id) {
        return new NotFoundException(id);
    }

    public static NotFoundException getNew(final String... name) {
        return new NotFoundException(name);
    }

    public static NotFoundException getNew(final Serializable... id) {
        return new NotFoundException(id);
    }

    public static void throwNew(final Serializable... id) {
        throw new NotFoundException(id);
    }

    public static void throwNew(final Serializable id) {
        throw new NotFoundException(id);
    }

    public static void throwNew(final String... id) {
        throw new NotFoundException(id);
    }

    public static void throwNew(final String id) {
        throw new NotFoundException(id);
    }
}
