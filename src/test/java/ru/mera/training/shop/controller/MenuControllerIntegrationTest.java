package ru.mera.training.shop.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.entity.Menu;
import ru.mera.training.shop.entity.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MenuControllerIntegrationTest {
    private static String ROOT;
    private static String ADD;
    private static String UPDATE;
    private static String GET_BY_ID;
    private static String GET_ALL;
    private static String DELETE;
    private static String CONTROLLER = "/menu";

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
    public void addMenu() {
        Menu menu = createMenu();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Menu> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Menu.class,
                menu.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Menu receivedMenu = responseEntity.getBody();
        assertNotNull(receivedMenu.getName());
    }

    private Menu createMenu() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Menu menu = prefilledMenu();

        HttpEntity<Menu> httpEntity = new HttpEntity<>(menu, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Menu createdMenu = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.PUT,
                httpEntity,
                Menu.class
        ).getBody();

        assertNotNull(createdMenu);
        assertEquals(menu.getName(), createdMenu.getName());

        return createdMenu;
    }

    private Menu prefilledMenu() {
        Menu eastMenu = new Menu();
        eastMenu.setName("East Menu");

        Product product1 = new Product();
        Product product2 = new Product();

        product1.setName("product1");
        product2.setName("product2");

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        eastMenu.setProducts(productList);

        return eastMenu;
    }
}
