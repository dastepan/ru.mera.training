package ru.mera.training.shop.controller;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mera.training.shop.dto.ProductDTO;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ProductControllerIntegrationTest {
    private static String ROOT; // = environment.getProperty("ROOT") + "/ingredients";
    private static String ADD; // = "/add";
    private static String UPDATE; // = "/update";
    private static String GET_BY_ID; // = "/get/id";
    private static String GET_ALL; // = "/get/all";
    private static String DELETE; // = "/delete/";
    private static final String CONTROLLER = "/product";

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
    public void addProduct() {
        Product product = createProduct();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> responseEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Product.class,
                product.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Product receivedProduct = responseEntity.getBody();
        assertNotNull(receivedProduct.getName());
    }

    @Test
    public void updateProduct() {
        Product product = createProduct();
        product.setName("name updated for test");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<Product> httpEntity = new HttpEntity<>(product, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> responseEntity = restTemplate.exchange(
                ROOT + UPDATE,
                HttpMethod.POST,
                httpEntity,
                Product.class
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        Product receivedProduct = responseEntity.getBody();
        assertNotNull(receivedProduct.getName());
        assertEquals("name updated for test", receivedProduct.getName());
    }

    @Test
    public void getProductById() {
        createProduct();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Set<ProductDTO>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<ProductDTO>>() {
                }
        );

        Set<ProductDTO> productSet = responseEntity.getBody();
        ProductDTO productDTO = productSet.iterator().next();

        ResponseEntity<Product> foundEntity = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Product.class,
                productDTO.getId()
        );
        assertNotNull(foundEntity.getBody());
        assertEquals(productDTO.getId(), foundEntity.getBody().getId());
        assertEquals(productDTO.getName(), foundEntity.getBody().getName());
        assertEquals(productDTO.getIngredientSet().size(), foundEntity.getBody().getIngredientSet().size());
    }

    @Test
    public void deleteProduct() {
        Product product = createProduct();

        ProductDTO productDTO = new ProductDTO();
        productDTO.getProductDTO(product);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ProductDTO> responseEntity = restTemplate.exchange(
                ROOT + DELETE + "/{id}",
                HttpMethod.DELETE,
                null,
                ProductDTO.class,
                product.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        ProductDTO receivedProductDTO = responseEntity.getBody();
        assertNotNull(receivedProductDTO.getName());

        ResponseEntity<Product> responseEntityForDeletedProduct = restTemplate.exchange(
                ROOT + GET_BY_ID + "/{id}",
                HttpMethod.GET,
                null,
                Product.class,
                product.getId()
        );

        assertEquals("OK", responseEntity.getStatusCode().getReasonPhrase());
        assertNull(responseEntityForDeletedProduct.getBody());
    }

    @Test
    public void getAllProducts() {
        createProduct();
        createProduct();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Set<ProductDTO>> responseEntity = restTemplate.exchange(
                ROOT + GET_ALL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Set<ProductDTO>>() {
                }
        );

        Set<ProductDTO> productSet = responseEntity.getBody();
        assertNotNull(productSet);
    }

    private Product createProduct() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Product product = prefilledProduct();

        HttpEntity<Product> httpEntity = new HttpEntity<>(product, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        Product createdProduct = restTemplate.exchange(
                ROOT + ADD,
                HttpMethod.PUT,
                httpEntity,
                Product.class
        ).getBody();

        return createdProduct;
    }

    private Product prefilledProduct() {
        Product product = new Product();
        product.setName("@test_product");

        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setName("testIngredient 1");
        ingredient.setCost(50);
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("testIngredient 2");
        ingredient.setCost(25);
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setName("testIngredient 3");
        ingredient.setCost(30);
        ingredients.add(ingredient);

        product.setIngredientSet(ingredients);

        return product;
    }
}
