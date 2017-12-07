package ru.mera.training.shop.dto;

import org.springframework.stereotype.Component;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProductDTO {
    private int id;
    private String name;
    private Set<IngredientDTO> ingredientSet = new HashSet<>();

    public Set<ProductDTO> getProductDTOSet(Set<Product> productSet) {
        Set<ProductDTO> productDTOSet = new HashSet<>();

        for (Product product : productSet) {
            Set<IngredientDTO> ingredientDTOS = new HashSet<>();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());

            for (Ingredient ingredient : product.getIngredientSet()) {
                IngredientDTO ingredientDTO = new IngredientDTO();
                ingredientDTO.setId(ingredient.getId());
                ingredientDTO.setName(ingredient.getName());
                ingredientDTO.setCost(ingredient.getCost());
                ingredientDTOS.add(ingredientDTO);
            }
            productDTO.setIngredientSet(ingredientDTOS);
            productDTOSet.add(productDTO);
        }
        return productDTOSet;
    }

    public ProductDTO getProductDTO(Product product) {
        setId(product.getId());
        setName(product.getName());
        Set<IngredientDTO> ingredientDTOS = new HashSet<>();
        for (Ingredient ingredient : product.getIngredientSet()) {
            IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setId(ingredient.getId());
            ingredientDTO.setName(ingredient.getName());
            ingredientDTO.setCost(ingredient.getCost());
            ingredientDTOS.add(ingredientDTO);
        }
        setIngredientSet(ingredientDTOS);
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<IngredientDTO> getIngredientSet() {
        return ingredientSet;
    }

    public void setIngredientSet(Set<IngredientDTO> ingredientSet) {
        this.ingredientSet = ingredientSet;
    }
}
