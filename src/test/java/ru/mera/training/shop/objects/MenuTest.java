package ru.mera.training.shop.objects;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MenuTest {
    private Menu menu;

    @Before
    public void init() throws IOException {
        Product product1 = null;
        Product product2;
        int count = 0;
        Set<Ingredient> ingredients = new HashSet<>();
        List<String> fileList = Files.readAllLines(Paths.get("src/test/resources/ingredients.txt"), StandardCharsets.UTF_8);
        for (String fileString : fileList) {
            if (count == 9) {
                product1 = new Product(1, ingredients, "вкусняшка");
                ingredients.clear();
            }
            String[] strings = fileString.split(",");
            ingredients.add(new Ingredient(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2])));
            count++;
        }
        product2 = new Product(2, ingredients, "пальчики оближешь");
        Set<Product> productSet = new HashSet<>();
        productSet.add(product1);
        productSet.add(product2);
        menu = new Menu(1, productSet);

    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(200, menu.getPrice());
    }

}