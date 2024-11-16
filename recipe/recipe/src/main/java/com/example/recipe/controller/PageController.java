package com.example.recipe.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PageController {
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

    // 마이페이지로 이동
//    @GetMapping("/mypage")
//    public String myPage(Model model) {
//        return "mypage";
//    }
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
}
