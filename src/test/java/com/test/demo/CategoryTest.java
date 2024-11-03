package com.test.demo;

import com.test.demo.entity.Recipe;
import com.test.demo.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void categoryTest(){
        List<Recipe> recipeList = categoryService.findByCategory("양식");
        if(recipeList.isEmpty()){
            System.out.println("검색 카테고리의 레시피가 없습니다.");
        }
        recipeList.forEach(recipe -> System.out.println("검색 결과: " + recipe.getTitle()));
    }
}
