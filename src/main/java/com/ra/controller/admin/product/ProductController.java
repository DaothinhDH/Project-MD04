package com.ra.controller.admin.product;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PropertySource("classpath:config.properties")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Value("${path}")
    private String path;

    @RequestMapping("product")
    public String product(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("product", products);
        return "admin/product/tables";
    }

    @GetMapping("add-product")
    public String addProduct(Model model) {
        List<Category> categoryList = categoryService.findAll();
        Product product = new Product();
        model.addAttribute("category", categoryList);
        model.addAttribute("product_add", product);
        return "admin/product/add-product";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") @NotNull Product product,
                                @RequestParam("img") MultipartFile file, Model model) {
        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);
        try {
            file.transferTo(destination);
            product.setImage("http://localhost:8080/upload/images/" + fileName);
            productService.saveOrUpdate(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/edit-product/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        List<Category> categoryList = categoryService.findAll();
        Product product = productService.findById(id);
        model.addAttribute("category", categoryList);
        model.addAttribute("product", product);
        return "admin/product/edit-product";
    }
    @PostMapping("update-product")
    public String updateProduct(@RequestParam("img") MultipartFile file,
                                @ModelAttribute("product") Product product) {
        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);
        try {
            if (!fileName.isEmpty()){
                file.transferTo(destination);
                product.setImage("http://localhost:8080/upload/images/" + fileName);
            }
            productService.saveOrUpdate(product);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }
}


