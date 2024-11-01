package com.ra.session01.model.repository.product;

import com.ra.session01.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByProductName(String productName);
    Product findProductById(long id);

}
