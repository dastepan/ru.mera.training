package ru.mera.sergeynazin.controller.advice;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Admin {
    boolean requireAdminRole() default true;
}
