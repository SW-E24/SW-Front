package com.example.recipe.Controller;

import com.example.recipe.Entity.User;
import com.example.recipe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/mypage")
    public ResponseEntity<User> getMyPageInfo(@PathVariable String userId) {
        User user = userService.getMyPageInfo(userId);  // 마이페이지 보기 - 내 정보 보기 메소드 추가
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/grade")
    public ResponseEntity<String> getUserLevel(@PathVariable String userId) {
        String grade = userService.getUserLevel(userId);  // 현재 등급 확인 메소드 추가
        return ResponseEntity.ok(grade);
    }
}
