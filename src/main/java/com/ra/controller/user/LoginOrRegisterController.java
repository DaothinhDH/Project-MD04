package com.ra.controller.user;

import com.ra.model.dto.user.UserRegisterDTO;
import com.ra.model.dto.user.response.UserResponseDTO;
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
        UserResponseDTO userResponseDTO = userService.checkLogin(user.getUserEmail(),user.getPassword());

        if (userResponseDTO != null && userResponseDTO.isRole() == true) {
            session.setAttribute("user",userResponseDTO);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        session.removeAttribute("user");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(Model model){
        UserRegisterDTO user = new UserRegisterDTO();
        model.addAttribute("user",user);
        return "users/login/login";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("user") UserRegisterDTO user) {
        if (userService.register(user)) {
            return "redirect:/";
        }
        return "redirect:/register";
    }

    @GetMapping("/login-admin")
    public String loginAdmin(){
        return "admin/loginAdmin";
    }
    @PostMapping("/handle-login")
    public String handleLogin(@RequestParam("email") String email ,@RequestParam("password") String password){
        UserResponseDTO userResponseDTO = userService.checkLogin(email,password);
        if (userResponseDTO != null){
            if (!userResponseDTO.isRole()){
                session.setAttribute("admin",userResponseDTO);
                return "redirect:/admin/";
            }
        }
        System.out.println(email);
        System.out.println(password);
        return "redirect:/login-admin";
    }

}