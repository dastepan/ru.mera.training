package ru.mera.training.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.mera.training.shop.entity.Product;
import ru.mera.training.shop.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public ProductService productService;

    @ResponseBody
    @RequestMapping(value = "/get/all", method = RequestMethod.GET)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @ResponseBody
    @RequestMapping(value = "/get/id/{id}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Product getProductById(@PathVariable(value = "id") String id){
        return productService.getProductById(Integer.parseInt(id));
    }

    @ResponseBody
    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public List<Product> getProductsByName(@PathVariable(value = "name") String name){
        return productService.getProductsByName(name);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.PUT, produces = "application/json;charset=utf-8")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Product updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Product deleteProduct(@PathVariable(value = "id") String id){
        return productService.deleteProduct(Integer.parseInt(id));
    }
}
