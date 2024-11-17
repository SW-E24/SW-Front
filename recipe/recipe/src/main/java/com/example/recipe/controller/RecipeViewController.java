package com.example.recipe.controller;

import org.springframework.ui.Model;
import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class RecipeViewController {

    @Autowired
    private RecipeViewService recipeService;

    @GetMapping("/post view")
    public String viewRecipe(@RequestParam("recipeId") Long recipeId, Model model) {
        // recipeId에 해당하는 레시피를 조회
        Recipe recipe = recipeService.findRecipeById(recipeId);

        if (recipe.getSteps() != null) {
            recipe.getSteps().forEach(step -> {
                if (step.getPhoto() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(step.getPhoto());
                    step.setDescription(step.getDescription() + "|data:image/jpeg;base64," + base64Image);
                }
            });
        }
        model.addAttribute("recipe", recipe);
        return "post view";
    }
}
