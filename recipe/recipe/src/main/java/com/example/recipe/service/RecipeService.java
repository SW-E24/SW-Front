package com.example.recipe.service;

import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    //recipe 엔터티와 데이터베이스 간의 CRUD 작업 수행하는 객체

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository; //초기화
    }

    // 조리 방법을 포함한 레시피 생성 메서드
    public Recipe createRecipe(String title, String category, List<Recipe.Ingredient> ingredients, List<String> steps, String description) {
        Recipe recipe = new Recipe(title, category, LocalDate.now(), ingredients, steps, description);
        return recipeRepository.save(recipe);
        //새 RECIPE 객체 생성, 현재 날짜 (LoacalDate.now()) 사용하여 date 필드 설정
        //생성된 레시피 데이터베이스에 저장
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
        //recipe 객체 받아서 저장
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
        //주어진 id로 레시피 조회, 결과가 있으면 recipe 객체 포함 없으면 empty 반환
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
        //모든 레시피 목록 조회
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
        }
        //주어진 id 로 기존 레시피를 찾는다
        // 레시피가 존재하면 -> existingRecipeOpt.isPresent() 가 true
        //기존 recipe 객체의 필드를 updatedRecipe 값으로 업데이트함
        else {
            throw new RuntimeException("Recipe not found");
        }
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
        //주어진 id에 해당하는 레시피 삭제
    }
}