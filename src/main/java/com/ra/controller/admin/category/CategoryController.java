package com.ra.controller.admin.category;
import com.ra.model.entity.Category;
import com.ra.model.service.category.CategoryService;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("/category")
    public String table(Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        return "admin/category/tables";
    }
    @GetMapping("/add-category")
    public String add(Model model){
        Category category = new Category();
        model.addAttribute("category_add", category);
        return "admin/category/add-category";
    }
    @PostMapping("/create-category")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()){
            return "admin/category/add-category";
        }else {
            categoryService.saveOrUpdate(category);
            return "redirect:/admin/category";
        }
    }
}
