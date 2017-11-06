package ru.mera.sergeynazin.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotEnoughPermissionException extends RuntimeException {
    public NotEnoughPermissionException(final String msg) {
        super(msg);
    }
}