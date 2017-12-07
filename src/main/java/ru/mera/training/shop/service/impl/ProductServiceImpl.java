package ru.mera.training.shop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mera.training.shop.dao.ProductDao;
import ru.mera.training.shop.dto.ProductDTO;
import ru.mera.training.shop.entity.Ingredient;
import ru.mera.training.shop.entity.Product;
import ru.mera.training.shop.service.ProductService;

import java.util.List;
import java.util.Set;

@Service("ProductService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductDTO productDTO;

//    @Override
//    public List<Product> getAllProducts() {
//        return productDao.getList();
//    }

    @Override
    public Set<ProductDTO> getAllProducts() {
        return (new ProductDTO().getProductDTOSet(productDao.getSet()));
    }

    @Override
    public ProductDTO getProductById(int id) {
        return productDTO.getProductDTO(productDao.getById(id));
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
    public ProductDTO deleteProduct(int id) {
        return (new ProductDTO()).getProductDTO(productDao.delete(id));
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productDao.getProductsByName(name);
    }

    @Override
    public Set<Ingredient> getIngredientSet(int id) {
        return productDao.getIngredientSet(id);
    }
}
