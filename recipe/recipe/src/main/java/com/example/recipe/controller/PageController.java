package com.example.recipe.controller;

import com.example.recipe.entity.Recipe;
import com.example.recipe.service.RecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pages")
public class PageController {

    @Autowired
    private RecipeService recipeService;

    // 회원가입 페이지로 이동 (index -> register)
    @GetMapping("/register")
    public String registerPage(Model model) {
        return "register";
    }

    // 로그인 페이지로 이동 (index -> login)
    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    // 로그인 상태가 아니라면 로그인 페이지로 이동하는걸로 수정
    @GetMapping("/mypage")
    public String myPage(HttpSession session) {
        if (session.getAttribute("currentUser") == null) {
            return "redirect:/pages/login"; // 로그인 상태가 아니면 로그인 페이지로 리다이렉트
        }
        return "mypage"; // 로그인 상태라면 마이페이지 반환
    }

    // 회원정보 수정 페이지로 이동
    @GetMapping("/profile")
    public String profilePage(Model model) {
        return "profile";
    }

    // 게시판 페이지로 이동 시 모든 게시물 들고옴
    @GetMapping("/board")
    public String getAllRecipe( @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "5") int size,
                                org.springframework.ui.Model model) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Recipe> recipePage;

        recipePage = recipeService.getAllRecipe(pageable);
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "board";
    }

}
