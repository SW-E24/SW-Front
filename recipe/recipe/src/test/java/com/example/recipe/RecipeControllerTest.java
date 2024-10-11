package com.example.recipe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.recipe.Entity.Recipe;
import com.example.recipe.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUpdateAndDeleteRecipeTest() throws Exception {
        // 1. 레시피 생성
        Map<String, Object> createRequest = new HashMap<>();
        createRequest.put("title", "Test Recipe");
        createRequest.put("category", "한식");

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("소금", "1 작은술"),
                new Ingredient("후추", "1/2 작은술")
        );
        List<String> steps = Arrays.asList(
                "고기를 준비합니다.",
                "소금과 후추로 고기를 간합니다."
        );

        createRequest.put("ingredients", ingredients);
        createRequest.put("steps", steps);
        createRequest.put("description", "테스트용 한식 레시피");

        String createJsonRequest = objectMapper.writeValueAsString(createRequest);

        // POST 요청으로 레시피 생성 후, 생성된 레시피 ID 가져오기
        String createdRecipeJson = mockMvc.perform(post("/api/recipes/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Recipe"))
                .andExpect(jsonPath("$.category").value("한식"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long recipeId = JsonPath.parse(createdRecipeJson).read("$.id", Long.class);

        // 2. 레시피 수정
        Map<String, Object> updateRequest = new HashMap<>();
        updateRequest.put("title", "Updated Test Recipe");
        updateRequest.put("category", "양식");

        List<Ingredient> updatedIngredients = Arrays.asList(
                new Ingredient("설탕", "2 큰술"),
                new Ingredient("간장", "3 큰술")
        );
        List<String> updatedSteps = Arrays.asList(
                "재료를 준비합니다.",
                "설탕과 간장을 섞어서 소스를 만듭니다."
        );

        updateRequest.put("ingredients", updatedIngredients);
        updateRequest.put("steps", updatedSteps);
        updateRequest.put("description", "업데이트된 테스트 레시피");

        String updateJsonRequest = objectMapper.writeValueAsString(updateRequest);

        // PUT 요청으로 레시피 수정
        mockMvc.perform(put("/api/recipes/" + recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateJsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Test Recipe"))
                .andExpect(jsonPath("$.category").value("양식"))
                .andExpect(jsonPath("$.ingredients[0].name").value("설탕"))
                .andExpect(jsonPath("$.steps[1]").value("설탕과 간장을 섞어서 소스를 만듭니다."));

        // 3. 레시피 삭제
        mockMvc.perform(delete("/api/recipes/" + recipeId))
                .andExpect(status().isOk())
                .andExpect(content().string("삭제 되었습니다"));

        // 삭제 후, 해당 ID로 조회 시 404 상태를 반환하는지 확인
        mockMvc.perform(get("/api/recipes/" + recipeId))
                .andExpect(status().isNotFound());

    }
}
