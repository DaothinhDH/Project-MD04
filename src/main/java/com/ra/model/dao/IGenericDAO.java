package com.ra.model.dao;

import com.ra.model.entity.Category.Category;

import java.util.List;

public interface IGenericDAO<T,ID> {
    List<T> findAll();
    T findById(ID id);
    boolean saveOrUpdate(T t);
}
