package ru.mera.training.shop.controller;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.entity.Ingredient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IngredientControllerIntegrationTest {
    private final String ROOT = "http://localhost:8080/ingredient";
    private final String ADD = "/add";
    private final String UPDATE = "/update";
    private final String GET_BY_ID = "/get";
    private final String DELETE = "/update";

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

//    @Test
//    public void deleteIngredient() {
//        Ingredient ingredient = createIngredient();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
//
//        HttpEntity<Ingredient> httpEntity = new HttpEntity<>(ingredient, httpHeaders);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<Ingredient> responseEntity = restTemplate.delete(
//                ROOT + DELETE,
//                ingredient.getId(),
//                ingredient.getName(),
//                ingredient.getCost()
//        );
//
//        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
//        Ingredient receivedIngredient = responseEntity.getBody();
//        assertNotNull(receivedIngredient.getName());
//    }

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
