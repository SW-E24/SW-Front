package com.example.recipe.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId; // 각 레시피 글 고유 ID (PK)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user; // 작성한 사용자 (FK)

    @ElementCollection
    private List<Ingredient> ingredients;

    @ElementCollection
    private List<String> steps;

    private String title; // 레시피 제목
    private String description; // 레시피 설명
    //private String ingredients; // 사용된 재료 목록
    private String category; // 레시피 카테고리
    private LocalDateTime date; // 레시피 작성일

    @Embeddable
    //별도의 테이블이 아닌 Recipe 테이블 내의 컬렉션으로 저장
    public static class Ingredient {
        private String name;
        private String quantity;

        public Ingredient() {}

        public Ingredient(String name, String quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    // Getters and Setters

    public Recipe() {
        // 기본 생성자
    }

    public Recipe(String title, String category, LocalDateTime date, List<Ingredient> ingredients, List<String> steps, String description) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.ingredients = ingredients;
        this.steps = steps;
        this.description = description;
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

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
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

