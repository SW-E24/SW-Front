package com.test.demo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(length = 300, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String ingredients;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime date;

    @Builder
    public Recipe(Long recipeId, String title, String description, String ingredients, String category, LocalDateTime date, String ingredients1) {
        this.recipeId = recipeId;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.category = category;
        this.date = date;
    }
}
