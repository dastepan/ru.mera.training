package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mera.training.shop.dto.ProductDTO;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;
import ru.mera.training.shop.service.ProductService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public ProductService productService;

    //    @ResponseBody
//    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
//    public List<Product> getAllProducts(){
//        return productService.getAllProducts();
//    }
    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public Set<ProductDTO> getAllProducts() {
        Set<ProductDTO> set = new HashSet<>();
        set.addAll(productService.getAllProducts());
        return set;
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public ProductDTO getProductById(@PathVariable(value = "id") String id) {
        return productService.getProductById(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Product> getProductsByName(@PathVariable(value = "name") String name) {
        return productService.getProductsByName(name);
    }
//
//    @ResponseBody
//    @RequestMapping(value = "/get/ingredients/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
//    public Set<Ingredient> getIngredientSetForProduct(@PathVariable(value = "id") String id){
//        return productService.getIngredientSet(Integer.parseInt(id));
//    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ProductDTO deleteProduct(@PathVariable(value = "id") String id) {
        return productService.deleteProduct(Integer.parseInt(id));
    }
}
