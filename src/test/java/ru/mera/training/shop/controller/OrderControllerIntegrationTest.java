package ru.mera.training.shop.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.entity.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class OrderControllerIntegrationTest {
    private static String ROOT; // = environment.getProperty("ROOT") + "/ingredients";
    private static String ADD; // = "/add";
    private static String UPDATE; // = "/update";
    private static String GET_BY_ID; // = "/get/id";
    private static String GET_ALL; // = "/get/all";
    private static String DELETE; // = "/delete/";
    private static final String CONTROLLER = "/order";

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
    public void addOrder() {
        Order order = createOrder();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Order.class,
                order.getOrderNumber()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Order receivedOrder = responseEntity.getBody();
        assertNotNull(receivedOrder.getOrderNumber());
    }

    @Test
    public void updateOrder() {
        Order order = createOrder();
        order.setDescription("test order");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<Order> httpEntity = new HttpEntity<>(order, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.POST,
                httpEntity,
                Order.class
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Order receivedOrder = responseEntity.getBody();
        assertNotNull(receivedOrder.getDescription());
        assertEquals("test order", receivedOrder.getDescription());
    }

    @Test
    public void deleteOrder(){
        Order order = createOrder();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                ROOT + DELETE + "/{id}",
                HttpMethod.DELETE,
                null,
                Order.class,
                order.getOrderNumber()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Order receivedOrder = responseEntity.getBody();
        assertNotNull(receivedOrder.getDescription());

        ResponseEntity<Order> responseEntityForDeletedOrder = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Order.class,
                order.getOrderNumber()
        );

        assertEquals("OK", responseEntityForDeletedOrder.getStatusCode().getReasonPhrase());
        assertNull(responseEntityForDeletedOrder.getBody());
    }

    @Test
    public void getAllOrders(){
        createOrder();
        createOrder();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {
                }
        );

        List<Order> orderList = responseEntity.getBody();
        assertNotNull(orderList.get(0));
        assertNotNull(orderList.get(1));
    }

    private Order createOrder() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Order order = prefillOrder();

        HttpEntity<Order> httpEntity = new HttpEntity<>(order, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Order createdOrder = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.PUT,
                httpEntity,
                Order.class
        ).getBody();

        assertNotNull(createdOrder);
        assertEquals(order.getDescription(), createdOrder.getDescription());
        return createdOrder;
    }

    private Order prefillOrder() {
        Order testOrder = new Order();
        testOrder.setDescription("order for testing");
        return testOrder;
    }
}
