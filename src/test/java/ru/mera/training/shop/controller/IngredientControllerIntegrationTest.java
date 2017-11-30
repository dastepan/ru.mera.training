package ru.mera.training.shop.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.entity.Ingredient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class IngredientControllerIntegrationTest {

    private static String ROOT; // = environment.getProperty("ROOT") + "/ingredients";
    private static String ADD; // = "/add";
    private static String UPDATE; // = "/update";
    private static String GET_BY_ID; // = "/get/id";
    private static String GET_ALL; // = "/get/all";
    private static String DELETE; // = "/delete/";
    private static final String CONTROLLER = "/ingredients";

    @BeforeClass
    public static void initVariables() throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("src/test/resources/testHostConfig.properties")));
        ROOT = props.getProperty("ROOT") + CONTROLLER;
        ADD = props.getProperty("ADD");
        UPDATE = props.getProperty("UPDATE");
        GET_BY_ID = props.getProperty("GET_BY_ID");
        GET_ALL = props.getProperty("GET_ALL");
        DELETE = props.getProperty("DELETE");
    }


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
        assertNotNull(ingredientList.get(0));
        assertNotNull(ingredientList.get(1));
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
