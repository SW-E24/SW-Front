package com.example.recipe.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId; // 각 북마크 한 ID (PK)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 북마크 한 사용자 (FK)

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe; // 어느 게시글 북마크 한 건지 (FK)

    private LocalDateTime date; // 북마크 한 시간

    // Getters and Setters
    public Long getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}

