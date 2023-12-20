package com.ra.model.dao.orderdetail;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import com.ra.model.entity.User;
import com.ra.model.service.order.OrderService;
import com.ra.model.service.product.ProductService;
import com.ra.utill.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailDaoIMPL implements OrderDetailDAO{
    @Autowired
    private OrderDetailDAO orderDetailDAO;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Override
    public void save(OrderDetail orderDetail) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement =    callableStatement = connection.prepareCall("CALL PROC_CREATE_ORDER_DETAIL(?,?,?,?)");;
            callableStatement.setInt(1,orderDetail.getOrder().getOderId());
            callableStatement.setInt(2,orderDetail.getProduct().getProductId());
            callableStatement.setInt(3,orderDetail.getQuantity());
            callableStatement.setDouble(4,orderDetail.getPrice());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<OrderDetail> findAll(Integer order_Id) {
      Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_SHOW_ORDER_DETAIL(?)");
            callableStatement.setInt(1,order_Id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                OrderDetail orderDetail = new OrderDetail();
                Order order = orderService.findById(resultSet.getInt("order_id"));
                orderDetail.setOrder(order);
                Product product = productService.findById(resultSet.getInt("product_id"));
                orderDetail.setProduct(product);
                orderDetail.setQuantity(resultSet.getInt("quanlity"));
                orderDetail.setPrice(resultSet.getFloat("price"));
                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDetailList;
    }
}
