package com.test.demo.service;

import com.test.demo.entity.Recipe;
import com.test.demo.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuService {

    private final RecipeRepository recipeRepository;

    public MenuService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe recommendRecipe() {
        return recipeRepository.findRandomRecipe(); // 랜덤 레시피 반환
    }
}
