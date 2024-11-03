package com.test.demo;

import com.test.demo.entity.Recipe;
import com.test.demo.service.RecipeSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeSearchTest {

    @Autowired
    private RecipeSearchService recipeSearchService;

    @Test
    public void testSearchRecipes() {
        // 된장찌개 제목 검색 후 출력
        List<Recipe> results = recipeSearchService.searchTitle("된장찌개");
        if(results.isEmpty()) {
            System.out.println("검색된 레시피가 없습니다.");
        }else{
            results.forEach(recipe -> System.out.println("검색 결과: " + recipe.getTitle()));
        }
    }

    @Test
    public void testSearchIngredients(){
        // 양파가 들어간 레시피 모두 출력
        List<Recipe> results = recipeSearchService.searchIngredients("양파");
        if(results.isEmpty()) {
            System.out.println("검색된 레시피가 없습니다.");
        }else{
            results.forEach(recipe -> System.out.println("검색 결과: " + recipe.getTitle() + " | 재료: " + recipe.getIngredients()));
        };
    }
}




