package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/{category}.html")
    public String getRecipesByCategory(@PathVariable("category") String category, Model model) {
        String categoryName;
        String templateName;
        switch (category) {
            case "korean":
                categoryName = "한식";
                templateName = "korean";
                break;
            case "chinese":
                categoryName = "중식";
                templateName = "chinese";
                break;
            case "japanese":
                categoryName = "일식";
                templateName = "japanese";
                break;
            case "western":
                categoryName = "양식";
                templateName = "western";
                break;
            case "dessert":
                categoryName = "디저트";
                templateName = "dessert";
                break;
            default:
                categoryName = "";
                templateName = "index";
        }

        List<Recipe> recipes = categoryService.findByCategory(categoryName);
        model.addAttribute("recipes", recipes);
        model.addAttribute("selectedCategory", categoryName);

        return templateName;
    }
}
