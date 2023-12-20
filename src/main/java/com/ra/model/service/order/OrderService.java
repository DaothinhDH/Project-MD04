package com.ra.model.service.order;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    Boolean order(Order order);
    List<Order> findAll();
    Order findById(Integer id);
    List<OrderDetail> showOrderDetailsByOrderId(Integer order_id);
    boolean updateStatus(Integer id,Integer status);
}
