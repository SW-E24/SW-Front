package com.example.recipe.controller;

import com.example.recipe.repository.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SiteLinkController {

    private final RecipeRepository recipeRepository;

    public SiteLinkController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    //프론트로 구현된 부분, 코드 삭제
//    @GetMapping("/site-link")
//    public String siteLink(Model model) {
//        // 레시피 게시글 내 사이트 연결
//        model.addAttribute("recipe", recipeRepository.findRandomRecipe());
//        return "/site-link";
//    }
}
