package com.example.recipe.controller;

import org.springframework.ui.Model;
import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/post view")
    public String viewRecipe(@RequestParam("recipeId") Long recipeId, Model model) {
        // recipeId에 해당하는 레시피를 조회
        Recipe recipe = recipeService.findRecipeById(recipeId);

        model.addAttribute("recipe", recipe);
        return "post view";
    }
}
