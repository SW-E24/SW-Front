package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.MenuRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuRecommendController {

    @Autowired
    private MenuRecommendService menuRecommendService;

    @GetMapping("/board")
    public String menuRecommend(Model model) {
        Recipe recipe = menuRecommendService.recommendRecipe();
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
        } else {
            System.out.println("추천할 메뉴가 없습니다.");
        }
        return "board";
    }
}
