package ru.mera.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.training.shop.dao.ProductDao;
import ru.mera.training.shop.entity.Product;
import ru.mera.training.shop.service.ProductService;

import java.util.List;

@Service("ProductService")
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getAllProducts() {
        return productDao.getList();
    }

    @Override
    public Product getProductById(int id) {
        return productDao.getById(id);
    }

    @Override
    public Product addProduct(Product product) {
        return productDao.add(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product deleteProduct(int id) {
        return productDao.delete(getProductById(id));
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productDao.getProductsByName(name);
    }
}
