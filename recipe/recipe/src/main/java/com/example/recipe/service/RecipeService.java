package com.example.recipe.service;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 조리 방법을 포함한 레시피 생성 메서드
    public Recipe createRecipe(String title, String category, List<Recipe.Ingredient> ingredients, List<String> steps, String description) {
        Recipe recipe = new Recipe(title, category, LocalDate.now(), ingredients, steps, description);
        return recipeRepository.save(recipe);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        Optional<Recipe> existingRecipeOpt = recipeRepository.findById(id);
        if (existingRecipeOpt.isPresent()) {
            Recipe existingRecipe = existingRecipeOpt.get();
            existingRecipe.setTitle(updatedRecipe.getTitle());
            existingRecipe.setCategory(updatedRecipe.getCategory());
            existingRecipe.setIngredients(updatedRecipe.getIngredients());
            existingRecipe.setDescription(updatedRecipe.getDescription());
            existingRecipe.setSteps(updatedRecipe.getSteps());
            return recipeRepository.save(existingRecipe);
        } else {
            throw new RuntimeException("Recipe not found");
        }
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
