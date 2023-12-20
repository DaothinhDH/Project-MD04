package com.ra.model.dao.order;
import com.ra.model.entity.Order;
import com.ra.model.entity.User;
import com.ra.model.service.user.UserServive;
import com.ra.utill.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Repository
public class OrderDaoIMPL implements OrderDAO{
    @Autowired
    UserServive userServive;
    @Override
    public Order findById(Integer id) {
        Connection connection = null;
        Order order = new Order();
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_ORDER_BY_ID(?) ");
            callableStatement.setInt(1,id);
            ResultSet resultSet =  callableStatement.executeQuery();
            if (resultSet.next()) {
                order.setOderId(resultSet.getInt("id"));
                order.setUser(userServive.findById(resultSet.getInt("user_id")));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setTotalPrice(resultSet.getFloat("total"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setOrderStatus(resultSet.getInt("order_status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public boolean updateStatus(Integer id,Integer status){
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_STATUS_ORDER(?,?)}");
            callableStatement.setInt(1,id);
            callableStatement.setInt(2,status);
            int check=callableStatement.executeUpdate();
            return check >0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveOrUpdate(Order order) {
        return false;
    }

    @Override
    public Order save(Order order) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("CALL PROC_CREATE_ORDER(?,?,?,?,?)");

            callableStatement.setInt(1, order.getUser().getUserId());
            callableStatement.setFloat(2, order.getTotalPrice());
            callableStatement.setString(3, order.getAddress());
            callableStatement.setString(4, order.getPhone());
            callableStatement.registerOutParameter(5,Types.INTEGER);

            int check = callableStatement.executeUpdate();
            if (check > 0) {
                order.setOderId(callableStatement.getInt(5));
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return null;
    }
    @Override
    public List<Order> findAll() {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        List<Order> orderList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_SHOW_ORDER()");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                Order order = new Order();
                order.setOderId(resultSet.getInt("id"));
                User user = userServive.findById(resultSet.getInt("user_id"));
                order.setUser(user);
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setTotalPrice(resultSet.getInt("total"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setOrderStatus(resultSet.getInt("order_status"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderList;
    }

}
