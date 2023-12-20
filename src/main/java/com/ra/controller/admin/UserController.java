package com.ra.controller.admin;

import com.ra.model.entity.User;
import com.ra.model.service.user.UserServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @RequestMapping("/user/{id}")
    public String updateStatus(@PathVariable("id")Integer id,
                               @RequestParam("status") Integer status,
                               Model model){
        boolean newStatus = (status==1);
        boolean updatedStatus = userServive.userUpdateStatus(id, newStatus);
        if (updatedStatus){
            List<User> users = userServive.findAll();
            model.addAttribute("users", users);
            return "admin/users/tables";
        }
        return null;
    }


}
