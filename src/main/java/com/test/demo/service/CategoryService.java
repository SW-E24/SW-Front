package com.test.demo.service;

import com.test.demo.entity.Recipe;
import com.test.demo.repository.RecipeRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryService {

    private final RecipeRepository recipeRepository;

    public CategoryService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    // 카테고리에 속하는 레시피를 검색해 리스트로 반환
    public List<Recipe> findByCategory(String category) {
        List<Recipe> recipes = recipeRepository.findByCategory(category);
        return recipes.isEmpty() ? new ArrayList<>() : recipes;
    }
}
