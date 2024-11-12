package com.example.recipe.controller;

import com.example.recipe.entity.Member;
import com.example.recipe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Member> getUser(@PathVariable String userId) {
        Member user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Member> updateUser(@PathVariable String userId, @RequestBody Member updatedUser) {
        Member user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/mypage")
    public ResponseEntity<Member> getMyPageInfo(@PathVariable String userId) {
        Member user = userService.getMyPageInfo(userId);  // 마이페이지 보기 - 내 정보 보기 메소드 추가
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/grade")
    public ResponseEntity<String> getUserLevel(@PathVariable String userId) {
        String grade = userService.getUserLevel(userId);  // 현재 등급 확인 메소드 추가
        return ResponseEntity.ok(grade);
    }
}
