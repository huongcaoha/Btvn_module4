package com.ra.session01.model.service.product;

import com.ra.session01.model.entity.Product;
import com.ra.session01.model.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> findAllByProductName(String productName);
    List<Product> findAll();
    Product AddProduct(Product product);
    Product findProductById(long id);
    Product updateProduct(Product product);
    void deleteProduct(Product product);
}
