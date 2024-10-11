package com.example.recipe.Controller;

import com.example.recipe.Entity.Like;
import com.example.recipe.Service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping
    public ResponseEntity<Like> addLike(@RequestBody Like like) {
        Like newLike = likeService.addLike(like);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLike);
    }
}

