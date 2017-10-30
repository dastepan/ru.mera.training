package ru.mera.training.shawerma.objects;

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
        Shaurma shaurma1 = null;
        Shaurma shaurma2;
        int count = 0;
        Set<Ingredient> ingredients = new HashSet<>();
        List<String> fileList = Files.readAllLines(Paths.get("src/test/resources/ingredients.txt"), StandardCharsets.UTF_8);
        for (String fileString : fileList) {
            if (count == 9) {
                shaurma1 = new Shaurma(1, ingredients, "вкусняшка");
                ingredients.clear();
            }
            String[] strings = fileString.split(",");
            ingredients.add(new Ingredient(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2])));
            count++;
        }
        shaurma2 = new Shaurma(2, ingredients, "пальчики оближешь");
        Set<Shaurma> shaurmaSet = new HashSet<>();
        shaurmaSet.add(shaurma1);
        shaurmaSet.add(shaurma2);
        menu = new Menu(1, shaurmaSet);

    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(200, menu.getPrice());
    }

}