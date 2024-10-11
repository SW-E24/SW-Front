package com.example.recipe.controller;

import com.example.recipe.dto.RecipeRequest;
import com.example.recipe.model.Recipe;
import com.example.recipe.model.Recipe.Ingredient;
import com.example.recipe.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 레시피 생성 엔드포인트
    @PostMapping("/create")
    public Recipe createRecipe(@RequestBody RecipeRequest recipeRequest) {
        // 모든 데이터를 RecipeRequest로 받아 서비스에 전달
        return recipeService.createRecipe(
                recipeRequest.getTitle(),
                recipeRequest.getCategory(),
                recipeRequest.getIngredients(),
                recipeRequest.getSteps(),
                recipeRequest.getDescription()
        );
    }


    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        return recipeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        try {
            Recipe recipe = recipeService.updateRecipe(id, updatedRecipe);
            return ResponseEntity.ok(recipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            recipeService.deleteRecipe(id);
            return ResponseEntity.ok("삭제 되었습니다");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/confirm")
    public ResponseEntity<String> confirmDeleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            return ResponseEntity.ok("정말 삭제하시겠습니까?");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
