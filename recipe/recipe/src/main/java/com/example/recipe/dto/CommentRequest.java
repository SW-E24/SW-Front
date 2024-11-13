package com.example.recipe.dto;

public class CommentRequest {
    private Long recipeId;
    private Long userId;
    private String content;

    // 기본 생성자
    public CommentRequest() {}

    // 모든 필드를 초기화하는 생성자
    public CommentRequest(Long recipeId, Long userId, String content) {
        this.recipeId = recipeId;
        this.userId = userId;
        this.content = content;
    }

    // Getters and Setters
    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
