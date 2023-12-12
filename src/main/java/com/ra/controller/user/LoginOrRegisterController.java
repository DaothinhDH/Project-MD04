package com.ra.controller.user;

import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import com.ra.model.service.user.UserServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginOrRegisterController {
    @Autowired
    HttpSession session;
    @Autowired
    UserServive userService;
    @GetMapping("/login")
    public String login(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "users/login/login";
    }
    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") User user,Model model){
        User authent = userService.checkLogin(user.getUserEmail(),user.getPassword());

        if (authent != null && authent.isRole()) {

            session.setAttribute("user",authent);
            return "redirect:/";
        } else {
            return "redirect:/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserResponseDTO user = new UserResponseDTO();
        model.addAttribute("user",user);
        return "users/login/login";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") UserResponseDTO user) {
        if (userService.register(user)) {
            return "redirect:/";
        }
        return "redirect:/register";
    }




}