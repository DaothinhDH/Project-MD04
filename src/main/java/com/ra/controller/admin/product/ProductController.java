package com.ra.controller.admin.product;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("product")
    public String product(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("product", products);
        return "admin/product/tables";
    }
    @GetMapping("add-product")
    public String addProduct(Model model){
        List<Category> categoryList = categoryService.findAll();
        Product product = new Product();
        model.addAttribute("category",categoryList);
        model.addAttribute("product_add", product);
        return "admin/product/add-product";
    }
    @PostMapping("/create-product")
    public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result){
        if (result.hasErrors()) {
            return "admin/product/add-product";
        }else {
            productService.saveOrUpdate(product);
            return "redirect:/admin/product";
        }
    }
}

