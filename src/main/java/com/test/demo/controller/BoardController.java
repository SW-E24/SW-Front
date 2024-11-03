package com.test.demo.controller;

import com.test.demo.repository.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    private final RecipeRepository recipeRepository;

    public BoardController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String board(){
        return "board";
    }

    @GetMapping("/board/search")
    public String searchPage(Model model) {
        // 검색어 입력을 위한 화면으로 이동
        return "search";
    }

    @GetMapping("/board/category")
    public String categoory(Model model) {
        // 카테고리 구분 화면 이동
        return "category";
    }

    @GetMapping("/board/siteLink")
    public String siteLink(Model model) {
        // 사이트 연결 화면 이동
        model.addAttribute("recipe", recipeRepository.findRandomRecipe());
        return "siteLink";
    }
}
