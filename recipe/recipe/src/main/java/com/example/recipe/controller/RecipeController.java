package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

//    @PostMapping
//    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
//        Recipe newRecipe = recipeService.createRecipe(recipe);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newRecipe);
//    }

    @PostMapping("/create")
    public Recipe createRecipe(@RequestParam String title,
                               @RequestParam String category,
                               @RequestBody List<Recipe.Ingredient> ingredients,
                               @RequestBody List<String> steps,
                               @RequestParam String description) {
        if (!category.matches("양식|한식|중식|일식")) {
            throw new IllegalArgumentException("카테고리는 양식, 한식, 중식, 일식 중 하나여야 합니다.");
        } //카테고리 값이 양식,한식,중식,일식 중 하나가 아닌 경우 IllegalArgumentException을 발생
        return recipeService.createRecipe(title, category, ingredients, steps, description);
        //레시피 생성
    }

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recipe>> getRecipesByUserId(@PathVariable String userId) { // 특정 사용자의 레시피 조회
        List<Recipe> recipes = recipeService.getRecipesByUserId(userId);
        return ResponseEntity.ok(recipes);
    }
}
