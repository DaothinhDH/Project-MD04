package com.ra.model.dao.category;

import com.ra.model.entity.Category.Category;
import com.ra.utill.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryDaoIMPL implements CategoryDAO {
    @Override
    public List<Category> findAll() {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        List<Category> categoryList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_CATEGORY()}");
            ResultSet resultSet = callableStatement.executeQuery();
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return categoryList;
    }

    @Override
    public Category findById(Integer id) {
        Connection connection = null;
        Category category = new Category();
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CATEGORY_BY_ID(?)}");
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                category.setCategoryId(resultSet.getInt("id"));
                category.setCategoryName(resultSet.getString("name"));
                category.setDescription(resultSet.getString("description"));
                category.setCategoryStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check;
        if (category.getCategoryId() == 0) {
            try {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_CATEGORY(?,?)}");
                callableStatement.setString(1,category.getCategoryName());
                callableStatement.setString(2,category.getDescription());
                check = callableStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_CATEGORY(?,?,?,?)}");
                callableStatement.setInt(1,category.getCategoryId());
                callableStatement.setString(2,category.getCategoryName());
                callableStatement.setString(3,category.getDescription());
                callableStatement.setBoolean(4,category.isCategoryStatus());
                check = callableStatement.executeUpdate();
                if (check > 0){
                    return true;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                ConnectionDatabase.closeConnection(connection);
            }
        }
        return false;
    }
}
