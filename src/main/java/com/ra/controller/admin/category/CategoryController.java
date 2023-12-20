package com.ra.controller.admin.category;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.category.CategoryService;
import com.ra.model.service.product.ProductService;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/category")
    public String table(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        return "admin/category/tables";
    }

    @GetMapping("/add-category")
    public String add(Model model) {
        Category category = new Category();
        model.addAttribute("category_add", category);
        return "admin/category/add-category";
    }

    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit-category/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit-category";
    }

    @PostMapping("update-category")
    public String edit(@ModelAttribute("category") Category category) {
        categoryService.saveOrUpdate(category);
        return "redirect:/admin/category";

    }
}
