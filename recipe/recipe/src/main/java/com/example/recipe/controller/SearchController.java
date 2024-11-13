package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private RecipeSearchService recipeSearchService;

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("searchType") String searchType,
                                @RequestParam("keyword") String keyword, Model model) {
        List<Recipe> recipes;

        if ("title".equals(searchType)) {
            // 제목으로 검색
            recipes = recipeSearchService.searchTitle(keyword);
        } else {
            // 재료로 검색
            recipes = recipeSearchService.searchIngredients(keyword);
        }

        model.addAttribute("recipes", recipes);
        model.addAttribute("keyword", keyword);
        return "search result";
    }
}
