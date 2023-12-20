package com.ra.model.dao.orderdetail;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO {
    void save(OrderDetail orderDetail);
    List<OrderDetail> findAll(Integer order_id);
}
