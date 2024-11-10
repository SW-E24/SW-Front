package com.example.recipe.controller;

import com.example.recipe.entity.Comment;
import com.example.recipe.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable String userId) {
        List<Comment> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(comments);
    }
}