package com.example.recipe.Service;

import com.example.recipe.Entity.Recipe;
import com.example.recipe.Entity.View;
import com.example.recipe.Repository.RecipeRepository;
import com.example.recipe.Repository.ViewRepository;
import com.example.recipe.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewService {
    @Autowired
    private ViewRepository viewRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    //조회수 증가
    public void incrementViewCount(Long recipeId) {
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
}

