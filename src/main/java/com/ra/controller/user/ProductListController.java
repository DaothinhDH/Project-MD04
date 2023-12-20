package com.ra.controller.user;

import com.ra.model.entity.Product;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductListController {
    @Autowired
    private ProductService productService;
    @RequestMapping("/{id}")
    public String productList(@PathVariable("id") Integer id, Model model){
        List<Product> productList = productService.findByIdCategory(id);
        model.addAttribute("product", productList);
        return "users/home";
    }

}
