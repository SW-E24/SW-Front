package com.test.demo.controller;

import com.test.demo.entity.Recipe;
import com.test.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public String getRecipesByCategory(@RequestParam("category") String category, Model model) {
        List<Recipe> recipes = categoryService.findByCategory(category);
        model.addAttribute("recipes", recipes);
        return "category"; // Thymeleaf 템플릿 파일 이름
    }
}
