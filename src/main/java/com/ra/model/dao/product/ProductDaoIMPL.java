package com.ra.model.dao.product;

import com.ra.model.dao.category.CategoryDAO;
import com.ra.model.entity.Product;
import com.ra.utill.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDaoIMPL implements ProductDAO{
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public List<Product> findAll() {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        List<Product> products = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_PRODUCT()}");
            ResultSet resultSet = callableStatement.executeQuery();
            resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStatus(resultSet.getBoolean("status"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Product findById(Integer id) {
        Connection connection = null;
        Product product = new Product();
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setStatus(resultSet.getBoolean("status"));
                product.setCategory(categoryDAO.findById(resultSet.getInt("category_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return product;
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check;
        try {
        if (product.getProductId() ==  0) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_PRODUCT(?,?,?,?,?,?)}");
                callableStatement.setString(1,product.getProductName());
                callableStatement.setString(2,product.getDescription());
                callableStatement.setFloat(3,product.getPrice());
                callableStatement.setInt(4,product.getStock());
                callableStatement.setBoolean(5,product.isStatus());
                callableStatement.setInt(6,product.getCategory().getCategoryId());
                check = callableStatement.executeUpdate();
        }else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?)}");
                callableStatement.setInt(1,product.getProductId());
                callableStatement.setString(2,product.getProductName());
                callableStatement.setString(3,product.getDescription());
                callableStatement.setFloat(4,product.getPrice());
                callableStatement.setInt(5,product.getStock());
                callableStatement.setBoolean(6,product.isStatus());
                callableStatement.setInt(7,product.getCategory().getCategoryId());
                check = callableStatement.executeUpdate();
                if (check > 0) {
                    return true;
                }

        }  } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }
}
