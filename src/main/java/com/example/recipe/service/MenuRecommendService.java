package com.example.recipe.service;

import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuRecommendService {

    private final RecipeRepository recipeRepository;

    public MenuRecommendService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
//프론트로 구현된 부분, 코드 삭제
//    public Recipe recommendRecipe() {return recipeRepository.findRandomRecipe(); // 랜덤 레시피 반환
//    }
}
