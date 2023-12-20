package com.ra.model.service.user;

import com.ra.model.dao.user.UserDAO;
import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponseDTO;
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
    public UserResponseDTO checkLogin(String email, String password) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            if (BCrypt.checkpw(password,user.getPassword())){
                UserResponseDTO userResponseDTO = new UserResponseDTO();
                userResponseDTO.setUserId(user.getUserId());
                userResponseDTO.setUserName(user.getUserName());
                userResponseDTO.setUserEmail(user.getUserEmail());
                userResponseDTO.setImage(user.getImage());
                userResponseDTO.setPhoneNumber(user.getPhoneNumber());
                userResponseDTO.setAddress(user.getAddress());
                userResponseDTO.setRole(user.isRole());
                return userResponseDTO;
            }
        }
        return null;
    }

    @Override
    public boolean register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        user.setUserName(userRegisterDTO.getUserName());
        user.setPassword(userRegisterDTO.getPassword());
        user.setUserEmail(userRegisterDTO.getUserEmail());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());
        user.setAddress(userRegisterDTO.getAddress());
        user.setImage(userRegisterDTO.getImage());
        user.setRole(userRegisterDTO.isRole());
        //
        String hasPassword = BCrypt.hashpw(userRegisterDTO.getPassword(),BCrypt.gensalt(12));
        user.setPassword(hasPassword);
        return userDAO.saveOrUpdate(user);
    }

    @Override
    public boolean userUpdateStatus(Integer id, boolean status) {
        return userDAO.userUpdateStatus(id,status);
    }
}
