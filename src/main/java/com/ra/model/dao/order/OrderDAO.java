package com.ra.model.dao.order;

import com.ra.model.dao.IGenericDAO;
import com.ra.model.entity.Order;

import java.util.List;

public interface OrderDAO extends IGenericDAO<Order, Integer> {
    Order save(Order order);
    List<Order> findAll();
    Order findById(Integer id);
    boolean updateStatus(Integer id,Integer status);
}
