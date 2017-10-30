package ru.mera.training.shawerma.objects;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class ShaurmaTest {
    private Shaurma shaurma;

    @Before
    public void init() throws IOException {
        int count = 0;
        Set<Ingredient> ingredients = new HashSet<>();
        List<String> fileList = Files.readAllLines(Paths.get("src/test/resources/ingredients.txt"), StandardCharsets.UTF_8);
        for (String fileString : fileList) {
            if (count == 9) {
                break;
            }
            String[] strings = fileString.split(",");
            ingredients.add(new Ingredient(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2])));
            count++;
        }
        shaurma = new Shaurma(1, ingredients, "вкусняшка");
    }

    @Test
    public void getCost() throws Exception {
        assertEquals(100, shaurma.getCost());
    }

}