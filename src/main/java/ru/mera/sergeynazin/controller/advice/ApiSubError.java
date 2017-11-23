package ru.mera.sergeynazin.controller.advice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonAutoDetect(fieldVisibility=JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ApiSubError {
    // TODO: 11/9/17 We may build an ApiErrors hierarchy later
}