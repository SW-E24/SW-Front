package com.example.recipe.service;

import com.example.recipe.controller.RecipeController;
import com.example.recipe.dto.RecipeRequest;
import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecipe_ValidInput_ShouldReturnRecipe() {
        RecipeRequest request = new RecipeRequest("title", "한식", List.of(), List.of(), "description");
        Recipe expectedRecipe = new Recipe("title", "한식", null, List.of(), List.of(), "description");

        when(recipeService.createRecipe(request.getTitle(), request.getCategory(), request.getIngredients(), request.getSteps(), request.getDescription()))
                .thenReturn(expectedRecipe);

        Recipe actualRecipe = recipeController.createRecipe(request);

        assertEquals(expectedRecipe.getTitle(), actualRecipe.getTitle());
        assertEquals(expectedRecipe.getCategory(), actualRecipe.getCategory());
        verify(recipeService, times(1)).createRecipe(request.getTitle(), request.getCategory(), request.getIngredients(), request.getSteps(), request.getDescription());
    }

    @Test
    void getAllRecipes_ShouldReturnListOfRecipes() {
        Recipe recipe1 = new Recipe("title1", "한식", null, List.of(), List.of(), "description1");
        Recipe recipe2 = new Recipe("title2", "양식", null, List.of(), List.of(), "description2");
        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        when(recipeService.getAllRecipes()).thenReturn(recipes);

        List<Recipe> actualRecipes = recipeController.getAllRecipes();

        assertEquals(2, actualRecipes.size());
        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void getRecipeById_ExistingId_ShouldReturnRecipe() {
        Recipe recipe = new Recipe("title", "한식", null, List.of(), List.of(), "description");
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

        ResponseEntity<Recipe> response = recipeController.getRecipeById(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(recipe, response.getBody());
        verify(recipeService, times(1)).getRecipeById(1L);
    }

    @Test
    void getRecipeById_NonExistingId_ShouldReturnNotFound() {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Recipe> response = recipeController.getRecipeById(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(recipeService, times(1)).getRecipeById(1L);
    }

    @Test
    void updateRecipe_ExistingId_ShouldReturnUpdatedRecipe() {
        RecipeRequest request = new RecipeRequest("updatedTitle", "양식", List.of(), List.of(), "updatedDescription");
        Recipe updatedRecipe = new Recipe("updatedTitle", "양식", null, List.of(), List.of(), "updatedDescription");

        when(recipeService.updateRecipe(1L, request)).thenReturn(updatedRecipe);

        ResponseEntity<Recipe> response = recipeController.updateRecipe(1L, request);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(updatedRecipe.getTitle(), response.getBody().getTitle());
        verify(recipeService, times(1)).updateRecipe(1L, request);
    }

    @Test
    void updateRecipe_NonExistingId_ShouldReturnNotFound() {
        RecipeRequest request = new RecipeRequest("updatedTitle", "양식", List.of(), List.of(), "updatedDescription");

        when(recipeService.updateRecipe(1L, request)).thenThrow(new RuntimeException("Recipe not found"));

        ResponseEntity<Recipe> response = recipeController.updateRecipe(1L, request);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(recipeService, times(1)).updateRecipe(1L, request);
    }

    @Test
    void deleteRecipe_ExistingId_ShouldReturnSuccessMessage() {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(new Recipe()));

        ResponseEntity<String> response = recipeController.deleteRecipe(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("삭제 되었습니다", response.getBody());
        verify(recipeService, times(1)).deleteRecipe(1L);
    }

    @Test
    void deleteRecipe_NonExistingId_ShouldReturnNotFound() {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = recipeController.deleteRecipe(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(recipeService, times(1)).getRecipeById(1L);
    }
}
