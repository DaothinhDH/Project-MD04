package com.ra.model.dao;

import java.util.List;

public interface IGenericDAO<T,ID> {
    List<T> findAll();
    T findById(ID id);
    boolean saveOrUpdate(T t);
}
