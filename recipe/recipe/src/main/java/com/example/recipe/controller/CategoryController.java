package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/category/{category}")
    public String getRecipesByCategory(@PathVariable("category") String category,
                                       @RequestParam(value="page", defaultValue="1") int page,
                                       @RequestParam(value="size", defaultValue="5") int size,
                                       Model model) {

        Pageable pageable = PageRequest.of(page-1, size);
        Page<Recipe> recipePage;

        String categoryName;
        String templateName;
        switch (category.toLowerCase()) {
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

        recipePage = categoryService.findByCategory(categoryName, pageable);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("selectedCategory", categoryName);

        return templateName;
    }
}
