package com.example.recipe.Service;

import com.example.recipe.Entity.Comment;
import com.example.recipe.Repository.CommentRepository;
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