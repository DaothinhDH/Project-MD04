package com.ra.controller.admin;

import com.ra.model.entity.Product;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;
    @RequestMapping("products")
    public String product(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("product", products);
        return "admin/products/tables";
    }
}

