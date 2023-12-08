package com.ra.controller.admin;
import com.ra.model.entity.Category.Category;
import com.ra.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping("category")
    public String table(Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        return "admin/category/tables";
    }
}