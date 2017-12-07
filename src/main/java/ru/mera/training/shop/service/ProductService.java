package ru.mera.training.shop.service;


import ru.mera.training.shop.dto.ProductDTO;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface ProductService {
//    /**
//     * method for receiving all products
//     *
//     * @return all products
//     **/
//    List<Product> getAllProducts();

    /**
     * method for receiving all products
     *
     * @return Collection of all products
     **/
    Collection<ProductDTO> getAllProducts();

    /**
     * method for receiving product by id
     *
     * @param id = product id
     * @return Product
     **/
    ProductDTO getProductById(int id);

    /**
     * method to add new product to DB
     *
     * @param product -  product that should be added to DB
     * @return Product
     **/
    Product addProduct(Product product);

    /**
     * method to update product in DB
     *
     * @param product - product that should be updated in DB
     * @return Product
     **/
    Product updateProduct(Product product);

    /**
     * method to delete product By Id from DB
     *
     * @param id product's id that should be deleted from DB
     * @return deleted product
     **/
    ProductDTO deleteProduct(int id);

    /**
     * method to get list of products which have specified name
     *
     * @param name product's name to search
     * @return List of Product with same name
     **/
    List<Product> getProductsByName(String name);

    /**
     * method to get set of ingredients for specified product
     *
     * @param id product's id to search
     * @return Set of Ingredient
     **/
    Set<Ingredient> getIngredientSet(int id);
}
