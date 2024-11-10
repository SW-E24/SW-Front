package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.entity.Recipe.Ingredient;
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
    public Recipe createRecipe(@RequestParam String title,
                               @RequestParam String category,
                               @RequestBody List<Ingredient> ingredients,
                               @RequestBody List<String> steps,
                               @RequestParam String description) {
        if (!category.matches("양식|한식|중식|일식")) {
            throw new IllegalArgumentException("카테고리는 양식, 한식, 중식, 일식 중 하나여야 합니다.");
        } //카테고리 값이 양식,한식,중식,일식 중 하나가 아닌 경우 IllegalArgumentException을 발생
        return recipeService.createRecipe(title, category, ingredients, steps, description);
        //레시피 생성
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id); //해당 레시피를 getRecipeById로 조회
        return recipeOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //존재하면 ResponseEntity.ok(recipe)로 반환
        //없으면 ResponseEntity.notFound().build()를 반환
        //조회된 레시피 객체 또는 404 Not Found 응답 반환
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe updatedRecipe) {
        try { //PathVarialbe로 id 받아오고 RequestBody로 업데이트할 recipe 데이터 받아옴
            Recipe recipe = recipeService.updateRecipe(id, updatedRecipe);
            //업데이트된 레시피 저장
            return ResponseEntity.ok(recipe);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            recipeService.deleteRecipe(id);
            return ResponseEntity.ok("삭제 되었습니다");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/confirm")
    public ResponseEntity<String> confirmDeleteRecipe(@PathVariable Long id) {
        Optional<Recipe> recipeOpt = recipeService.getRecipeById(id);
        if (recipeOpt.isPresent()) {
            return ResponseEntity.ok("정말 삭제하시겠습니까?");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}