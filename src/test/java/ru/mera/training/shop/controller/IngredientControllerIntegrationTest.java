package ru.mera.training.shop.controller;

import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.entity.Ingredient;

import java.util.List;

import static org.junit.Assert.*;

public class IngredientControllerIntegrationTest {
    private final String ROOT = "http://localhost:8080/ingredients";
    private final String ADD = "/add";
    private final String UPDATE = "/update";
    private final String GET_BY_ID = "/get/id";
    private final String GET_ALL = "/get/all";
    private final String DELETE = "/delete/";

    @Test
    public void addIngredient() {
        Ingredient ingredient = createIngredient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Ingredient> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Ingredient.class,
                ingredient.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Ingredient receivedIngredient = responseEntity.getBody();
        assertNotNull(receivedIngredient.getName());
    }

    @Test
    public void updateIngredient() {
        Ingredient ingredient = createIngredient();
        ingredient.setName("Milk");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<Ingredient> httpEntity = new HttpEntity<>(ingredient, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Ingredient> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.POST,
                httpEntity,
                Ingredient.class
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Ingredient receivedIngredient = responseEntity.getBody();
        assertNotNull(receivedIngredient.getName());
        assertEquals("Milk", receivedIngredient.getName());
    }

    @Test
    public void deleteIngredient() {
        Ingredient ingredient = createIngredient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Ingredient> responseEntity = restTemplate.exchange(
                ROOT + DELETE + "/{id}",
                HttpMethod.DELETE,
                null,
                Ingredient.class,
                ingredient.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Ingredient receivedIngredient = responseEntity.getBody();
        assertNotNull(receivedIngredient.getName());

        ResponseEntity<Ingredient> responseEntityForDeletedIngredient = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Ingredient.class,
                ingredient.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNull(responseEntityForDeletedIngredient.getBody());
    }

    @Test
    public void getAllIngredients() {
        createIngredient();
        createIngredient();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Ingredient>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Ingredient>>() {

                }
        );

        List<Ingredient> ingredientList = responseEntity.getBody();
        assertNotNull(ingredientList.get(1));
        assertNotNull(ingredientList.get(2));
    }

    private Ingredient createIngredient() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Ingredient ingredient = prefillIngredient();

        HttpEntity<Ingredient> httpEntity = new HttpEntity<>(ingredient, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Ingredient createdIngredient = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.PUT,
                httpEntity,
                Ingredient.class
        ).getBody();

        assertNotNull(createdIngredient);
        assertEquals(ingredient.getName(), createdIngredient.getName());

        return createdIngredient;
    }

    private Ingredient prefillIngredient() {
        Ingredient ham = new Ingredient();
        ham.setName("Ham");
        ham.setCost(20);
        return ham;
    }
}
