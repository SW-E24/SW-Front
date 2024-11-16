package com.example.recipe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//페이지 이동 Controller 새로 생성.
@Controller
@RequestMapping("/recipes")
public class RecipePageController {

    @GetMapping("/create")
    public String showCreatePage() {
        return "post-register";
    }
}
