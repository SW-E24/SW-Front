package com.example.recipe.service;
import com.example.recipe.controller.RecipeController;
import com.example.recipe.dto.RecipeRequest;
import com.example.recipe.entity.Member;
import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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

    @Mock
    private GradeService gradeService;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRecipe_ValidInput_ShouldReturnRecipe() {
        // Given
        RecipeRequest request = new RecipeRequest("title", "한식", List.of(), List.of(), "description");
        Member mockUser = new Member(); // 테스트용 Member 객체 생성
        Recipe expectedRecipe = new Recipe("title", "한식", LocalDateTime.now(), List.of(), List.of(), "description");

        // 세션에서 currentUser로 설정된 사용자 객체 반환
        when(session.getAttribute("currentUser")).thenReturn(mockUser);

        // RecipeService의 createRecipe 호출 시 mockUser와 요청 데이터를 사용하도록 설정
        when(recipeService.createRecipe(
                mockUser,
                request.getTitle(),
                request.getCategory(),
                request.getIngredients(),
                request.getSteps(),
                request.getDescription()
        )).thenReturn(expectedRecipe);

        // When
        Recipe actualRecipe = recipeController.createRecipe(request, session);

        // Then
        assertEquals(expectedRecipe.getTitle(), actualRecipe.getTitle());
        assertEquals(expectedRecipe.getCategory(), actualRecipe.getCategory());
        assertEquals(expectedRecipe.getUser(), actualRecipe.getUser()); // 작성자 정보도 일치하는지 확인
        verify(recipeService, times(1)).createRecipe(
                mockUser,
                request.getTitle(),
                request.getCategory(),
                request.getIngredients(),
                request.getSteps(),
                request.getDescription()
        );
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
    void deleteRecipe_ExistingId_ShouldReturnSuccessMessageAndDecreasePostCount() {
        Recipe recipe = new Recipe();
        Member mockUser = new Member();
        recipe.setUser(mockUser); // 레시피의 작성자를 설정

        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));

        // 게시글 수 감소 및 등급 업데이트 메서드 검증
        doNothing().when(gradeService).decreasePostCount(mockUser.getUserId());
        doNothing().when(gradeService).updateMemberGreade(mockUser.getUserId());

        ResponseEntity<String> response = recipeController.deleteRecipe(1L);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("삭제 되었습니다", response.getBody());

        // 게시글 삭제와 관련된 서비스 메서드 호출 횟수 검증
        verify(recipeService, times(1)).deleteRecipe(1L);
        verify(gradeService, times(1)).decreasePostCount(mockUser.getUserId()); // 게시글 수 감소 확인
        verify(gradeService, times(1)).updateMemberGreade(mockUser.getUserId()); // 등급 갱신 확인
    }

    @Test
    void deleteRecipe_NonExistingId_ShouldReturnNotFound() {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = recipeController.deleteRecipe(1L);

        assertTrue(response.getStatusCode().is4xxClientError());
        verify(recipeService, times(1)).getRecipeById(1L);
    }
}
