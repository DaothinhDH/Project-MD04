package com.ra.model.service.user;

import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;

import java.util.List;

public interface UserServive {
    List<User> findAll();
    User findById(Integer id);
    boolean saveOrUpdate(User user);
    User findByEmail(String email);
    User checkLogin(String email, String password);
    boolean register(UserResponseDTO userResponseDTO);
}
