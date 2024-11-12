package com.example.recipe;

import com.example.recipe.dto.RecipeRequest;
import com.example.recipe.entity.Recipe;
import com.example.recipe.repository.RecipeRepository;
import com.example.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RecipeServiceTests {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void testCreateRecipe() {
        // Arrange
        String title = "Delicious Recipe";
        String category = "한식";
        List<Recipe.Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Recipe.Ingredient("소금", "1 작은술"));
        ingredients.add(new Recipe.Ingredient("후추", "1/2 작은술"));
        List<String> steps = new ArrayList<>();
        steps.add("고기를 준비합니다.");
        steps.add("소금과 후추로 간을 합니다.");
        steps.add("오븐에서 굽습니다.");
        String description = "맛있는 레시피";

        // Act
        Recipe createdRecipe = recipeService.createRecipe(title, category, ingredients, steps, description);

        // Assert
        assertNotNull(createdRecipe);
        assertEquals(title, createdRecipe.getTitle());
        assertEquals(category, createdRecipe.getCategory());
        assertEquals(ingredients, createdRecipe.getIngredients());
        assertEquals(steps, createdRecipe.getSteps());
        assertEquals(description, createdRecipe.getDescription());
    }

    @Test
    void testSaveRecipe() {
        // Arrange
        Recipe recipe = new Recipe("Delicious Recipe", "한식", LocalDate.now(), new ArrayList<>(), new ArrayList<>(), "맛있는 레시피");

        // Act
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        // Assert
        assertNotNull(savedRecipe);
        assertEquals(recipe, savedRecipe);
    }

    @Test
    void testGetRecipeById() {
        // Arrange
        Recipe recipe = new Recipe("Delicious Recipe", "한식", LocalDate.now(), new ArrayList<>(), new ArrayList<>(), "맛있는 레시피");
        Recipe savedRecipe = recipeRepository.save(recipe);
        Long recipeId = savedRecipe.getId();

        // Act
        Optional<Recipe> foundRecipe = recipeService.getRecipeById(recipeId);

        // Assert
        assertTrue(foundRecipe.isPresent());
        assertEquals(recipe, foundRecipe.get());
    }

    @Test
    void testUpdateRecipe() {
        // Arrange
        Recipe existingRecipe = new Recipe("Original Recipe", "한식", LocalDate.now(), new ArrayList<>(), new ArrayList<>(), "맛있는 레시피");
        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        Long recipeId = savedRecipe.getId();
        Recipe updatedRecipe = new Recipe("Updated Recipe", "중식", LocalDate.now(), new ArrayList<>(), new ArrayList<>(), "맛있는 중식 레시피");

        // Act
        RecipeRequest recipeRequest = null;
        Recipe result = recipeService.updateRecipe(recipeId, recipeRequest);

        // Assert
        assertNotNull(result);
        assertEquals(updatedRecipe.getTitle(), result.getTitle());
        assertEquals(updatedRecipe.getCategory(), result.getCategory());
        assertEquals(updatedRecipe.getDescription(), result.getDescription());
    }

    @Test
    void testDeleteRecipe() {
        // Arrange
        Long recipeId = 1L;
        Recipe recipe = new Recipe("Delicious Recipe", "한식", LocalDate.now(), new ArrayList<>(), new ArrayList<>(), "맛있는 레시피");
        recipeRepository.save(recipe);

        // Act
        recipeService.deleteRecipe(recipeId);

        // Assert
        assertTrue(recipeRepository.findById(recipeId).isEmpty());
    }
}