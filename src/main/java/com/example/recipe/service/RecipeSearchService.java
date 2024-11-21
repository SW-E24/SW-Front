//package com.example.recipe.service;
//
//
//import com.example.recipe.entity.Recipe;
//import com.example.recipe.repository.RecipeRepository;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class RecipeSearchService {
//
//    private final RecipeRepository recipeRepository;
//
//    public RecipeSearchService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }
//
//    // 레시피 가져와서 보여주기
//    public Recipe findRecipeById(Long recipeId) {
//        return recipeRepository.findById(recipeId).orElse(null);
//    }
//
//    // 제목으로 레시피 검색
//    public List<Recipe> searchTitle(String keyword) {
//        List<Recipe> recipes = recipeRepository.findByTitleContaining(keyword);
//        if (recipes.isEmpty()) {
//            System.out.println("검색 결과가 없습니다.");
//        }
//        return recipes;
//    }
//
//    // 재료로 레시피 검색
//    public List<Recipe> searchIngredients(String keyword) {
//        List<Recipe> recipes = recipeRepository.findByIngredientName(keyword);
//        if (recipes.isEmpty()) {
//            System.out.println("검색 결과가 없습니다.");
//        }
//        return recipes;
//    }
//}

//전체 코드 수정, 위 코드는 혹시나해서 남겨둠

package com.example.recipe.service;

import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeSearchService {

    private final RecipeRepository recipeRepository;

    public RecipeSearchService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 레시피 가져와서 보여주기
    public Recipe findRecipeById(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    // 제목으로 레시피 검색
    public Page<Recipe> searchTitle(String keyword, Pageable pageable) {
        return recipeRepository.findByTitleContaining(keyword, pageable);
    }

    // 재료로 레시피 검색
    public Page<Recipe> searchIngredients(String keyword, Pageable pageable) {
        return recipeRepository.findByIngredientContaining(keyword, pageable);
    }
}