package com.test.demo.service;


import com.test.demo.entity.Recipe;
import com.test.demo.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeSearchService {

    private final RecipeRepository recipeRepository;

    public RecipeSearchService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 제목으로 레시피 검색
    public List<Recipe> searchTitle(String keyword) {
        List<Recipe> recipes = recipeRepository.findByTitleContaining(keyword);
        if (recipes.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        }
        return recipes;
    }

    // 재료로 레시피 검색
    public List<Recipe> searchIngredients(String keyword) {
        List<Recipe> recipes = recipeRepository.findByIngredientsContaining(keyword);
        if (recipes.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        }
        return recipes;
    }
}