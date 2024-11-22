package com.example.recipe.service;

import com.example.recipe.entity.Recipe;
import com.example.recipe.entity.View;
import com.example.recipe.repository.RecipeRepository;
import com.example.recipe.repository.ViewRepository;
import com.example.recipe.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public void incrementViewCount(Long recipeId) { //조회수 증가 메소드
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + recipeId));

        View view = viewRepository.findByRecipeRecipeId(recipeId)
                .orElseGet(() -> {
                    View newView = new View();
                    newView.setRecipe(recipe); // 조회된 레시피 객체를 설정
                    newView.setCountView(0);
                    return newView;
                });
        view.setCountView(view.getCountView() + 1);
        viewRepository.save(view);
    }

    // 현재 보고있는 레시피 게시글의 아이디로 해당 게시글의 조회수 가져오기
    public int getViewCountByRecipeId(Long recipeId) {
        return viewRepository.findByRecipeRecipeId(recipeId)
                .map(View::getCountView).orElse(0); // 조회수가 없으면 0 반환
    }
}

