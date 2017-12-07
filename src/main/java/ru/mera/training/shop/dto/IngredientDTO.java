package ru.mera.training.shop.dto;

import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.HashSet;
import java.util.Set;

public class IngredientDTO {
    private int id;
    private String name;
    private int cost;
    private Set<ProductDTO> productSet = new HashSet<>();

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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Set<ProductDTO> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<ProductDTO> productSet) {
        this.productSet = productSet;
    }

    public IngredientDTO getIngredientDTO(Ingredient ingredient) {
        setId(ingredient.getId());
        setName(ingredient.getName());
        setCost(ingredient.getCost());
       // Set<ProductDTO> productDTOSet = new HashSet<>();
        for (Product product : ingredient.getProductSet()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productSet.add(productDTO);
        }
        //setIngredientSet(ingredientDTOS);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IngredientDTO)) return false;

        IngredientDTO that = (IngredientDTO) o;

        if (getId() != that.getId()) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
