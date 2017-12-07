package ru.mera.training.shop.dto;

import org.junit.Test;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ProductDTOUnitTest {
    @Test
    public void getProductDTOTest() throws Exception {
        Product product1 = createProduct();
        Product product2 = createProduct();
        Product product3 = createProduct();

        Set<Product> productSet = new HashSet<>();

        productSet.add(product1);
        productSet.add(product2);
        productSet.add(product3);

        ProductDTO productDTO = new ProductDTO();

        for (Product product : productSet) {
            productDTO.getProductDTO(product);
            assertEquals(product.getName(), productDTO.getName());
            assertEquals(product.getIngredientSet().size(), productDTO.getIngredientSet().size());
        }

        Product product4 = createProduct();
        productSet.add(product4);

        for (Product product : productSet) {
            productDTO.getProductDTO(product);
            assertEquals(product.getName(), productDTO.getName());
            assertEquals(product.getIngredientSet().size(), productDTO.getIngredientSet().size());
        }

    }

    private Product createProduct() {
        Product product = new Product();
        product.setName("testProduct");
        product.setIngredientSet(createIngredientSet());
        return product;
    }

    private Set<Ingredient> createIngredientSet() {
        Set<Ingredient> ingredients = new HashSet<>();

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setName("test ingredient 1");
        ingredient1.setCost(20);
        ingredients.add(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setName("test ingredient 2");
        ingredient2.setCost(10);

        ingredients.add(ingredient2);

        return ingredients;
    }

}