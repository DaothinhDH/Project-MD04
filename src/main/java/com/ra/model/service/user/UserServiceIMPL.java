package com.ra.model.service.user;

import com.ra.model.dao.user.UserDAO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceIMPL implements UserServive{
    @Autowired
    private UserDAO userDAO;
    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userDAO.findById(id);
    }

    @Override
    public boolean saveOrUpdate(User user) {
        return userDAO.saveOrUpdate(user);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User checkLogin(String email, String password) {
        return userDAO.checkLogin(email, password);
    }

    @Override
    public boolean register(UserResponseDTO userResponseDTO) {
        User user = new User();
        user.setUserName(userResponseDTO.getUserName());
        user.setPassword(userResponseDTO.getPassword());
        user.setUserEmail(userResponseDTO.getUserEmail());
        user.setPhoneNumber(userResponseDTO.getPhoneNumber());
        user.setAddress(userResponseDTO.getAddress());
        user.setImage(userResponseDTO.getImage());
        user.setRole(userResponseDTO.isRole());
        //
        String hasPassword = BCrypt.hashpw(userResponseDTO.getPassword(),BCrypt.gensalt(12));
        user.setPassword(hasPassword);
        return userDAO.saveOrUpdate(user);
    }
}
