package com.ra.model.service.product;

import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Integer id);
    boolean saveOrUpdate(Product product);
}
