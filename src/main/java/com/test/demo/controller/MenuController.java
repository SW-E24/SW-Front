package com.test.demo.controller;

import com.test.demo.entity.Recipe;
import com.test.demo.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/board/menu")
    public String menuRecommend(Model model) {
        Recipe recipe = menuService.recommendRecipe();
        if (recipe != null) {
            model.addAttribute("recipe", recipe);
        } else {
            System.out.println("추천할 메뉴가 없습니다.");
        }
        return "menu";
    }
}
