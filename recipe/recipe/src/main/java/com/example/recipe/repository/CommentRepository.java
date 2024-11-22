package com.example.recipe.repository;

import com.example.recipe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.domain.Pageable;//
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipeId(Long recipeId);

    List<Comment> findAllByUserId(String userId);

    // 로그인한 사용자가 작성한 댓글을 페이지네이션하여 가져오는 메서드
    Page<Comment> findAllByUserId(String userId, Pageable pageable);

}