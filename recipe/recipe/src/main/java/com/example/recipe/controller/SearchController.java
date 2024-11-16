//package com.example.recipe.controller;
//
//import com.example.recipe.entity.Recipe;
//import com.example.recipe.service.RecipeSearchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//public class SearchController {
//
//    @Autowired
//    private RecipeSearchService recipeSearchService;
//
//    @GetMapping("/search")
//    public String searchRecipes(@RequestParam("searchType") String searchType,
//                                @RequestParam("keyword") String keyword, Model model) {
//        List<Recipe> recipes;
//
//        if ("title".equals(searchType)) {
//            // 제목으로 검색
//            recipes = recipeSearchService.searchTitle(keyword);
//        } else {
//            // 재료로 검색
//            recipes = recipeSearchService.searchIngredients(keyword);
//        }
//
//        model.addAttribute("recipes", recipes);
//        model.addAttribute("keyword", keyword);
//        return "search result";
//    }
//}

//전체 코드 수정, 위 코드는 혹시나해서 남겨둠
package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private RecipeSearchService recipeSearchService;

    @GetMapping("/search")
    public String searchRecipes(@RequestParam("searchType") String searchType,
                                @RequestParam("keyword") String keyword,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                Model model) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Recipe> recipePage;

        if ("title".equals(searchType)) {
            // 제목으로 검색
            recipePage = recipeSearchService.searchTitle(keyword, pageable);
        } else if ("ingredient".equals(searchType)){
            // 재료로 검색
            recipePage = recipeSearchService.searchIngredients(keyword, pageable);
        } else {
            recipePage = Page.empty(pageable);
        }

        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);

        return "search";
    }
}
