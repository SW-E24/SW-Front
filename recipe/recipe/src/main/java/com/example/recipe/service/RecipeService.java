package com.example.recipe.service;

import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public Recipe createRecipe(String title, String category, List<Recipe.Ingredient> ingredients, List<String> steps, String description) {
        Recipe recipe = new Recipe(title, category, LocalDateTime.now(), ingredients, steps, description);
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipes() { //모든 레시피 가져오는 메소드
        return recipeRepository.findAll();
    }

    public List<Recipe> getRecipesByUserId(String userId) { // 특정 사용자의 레시피만 가져오는 메서드
        return recipeRepository.findAllByUserUserId(userId);
    }
}
