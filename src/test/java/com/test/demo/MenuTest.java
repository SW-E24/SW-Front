package com.test.demo;

import com.test.demo.entity.Recipe;
import com.test.demo.service.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {

    @Autowired
    private MenuService menuService;

    @Test
    public void MenuRecommendTest(){
        Recipe recipe = menuService.recommendRecipe();
        System.out.println("추천 메뉴: " + recipe.getTitle());
    }
}
