package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RecipeImageController {

    @Autowired
    private RecipeViewService recipeService;

    @GetMapping("/image/{recipeId}/{stepIndex}")
    public ResponseEntity<byte[]> getStepImage(@PathVariable Long recipeId, @PathVariable int stepIndex) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (recipe != null && stepIndex < recipe.getSteps().size()) {
            byte[] photo = recipe.getSteps().get(stepIndex).getPhoto();
            if (photo != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(photo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
