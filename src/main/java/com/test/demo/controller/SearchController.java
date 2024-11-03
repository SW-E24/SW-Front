package com.test.demo.controller;

import com.test.demo.entity.Recipe;
import com.test.demo.service.RecipeSearchService;
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
    public String searchRecipes(@RequestParam("keyword") String keyword,
                                @RequestParam("searchType") String searchType, Model model) {
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
        return "recipeSearchResult";
    }
}
