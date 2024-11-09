package com.example.recipe.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId; // 각 레시피 글 고유 ID (PK)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 작성한 사용자 (FK)

    private String title; // 레시피 제목
    private String description; // 레시피 설명
    private String ingredients; // 사용된 재료 목록
    private String category; // 레시피 카테고리
    private LocalDateTime date; // 레시피 작성일

    // Getters and Setters

    public Recipe() {
        // 기본 생성자
    }

    public Recipe(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

