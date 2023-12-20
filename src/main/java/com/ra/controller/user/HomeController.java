package com.ra.controller.user;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    HttpSession session;
    @RequestMapping("/")
    public String home(Model model){
        List<Product> products = productService.findAll();
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("product", products);
        session.setAttribute("category", categoryList);
        return "users/home";
    }

}
