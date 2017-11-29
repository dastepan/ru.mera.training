package ru.mera.training.shop.service;


import org.springframework.security.access.method.P;
import ru.mera.training.shop.entity.Product;

import java.util.List;

public interface ProductService {
    /**
     * method for receiving all products
     *
     * @return all products
     **/
    List<Product> getAllProducts();

    /**
     * method for receiving product by id
     *
     * @param id = product id
     * @return Product
     **/
    Product getProductById(int id);

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
     * @param product  - product that should be updated in DB
     * @return Product
     **/
    Product updateProduct(Product product);
    /**
     * method to delete product By Id from DB
     *
     * @param id  product's id that should be deleted from DB
     * @return deleted product
     **/
    Product deleteProduct(int id);
    /**
     * method to get list of products which have specified name
     *
     * @param name   product's name to search
     * @return List of Product with same name
     **/
    List<Product> getProductsByName(String name);
}
