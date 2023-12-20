package com.ra.model.dao.product;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductDAO extends IGenericDAO<Product,Integer> {
    List<Product> findByIdCategory(Integer categoryId);
}
