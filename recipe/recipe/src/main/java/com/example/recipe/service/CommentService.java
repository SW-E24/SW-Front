package com.example.recipe.service;

import com.example.recipe.entity.Comment;
import com.example.recipe.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByUserId(String userId) { //작성한 댓글을 조회하는 메서드
        return commentRepository.findAllByUserId(userId);
    }
}