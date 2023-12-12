package com.ra.controller.admin;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    private UserServive userServive;
    @RequestMapping("user")
    public String table(Model model) {
        List<User> users = userServive.findAll();
        model.addAttribute("users", users);
        return "admin/users/tables";
    }
}
