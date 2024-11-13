package com.example.recipe.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @Column(length = 300, nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime date;

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<com.example.recipe.entity.Recipe.Ingredient> ingredients;

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<com.example.recipe.entity.Recipe.Step> steps;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder
    public Recipe(Long recipeId, Member user, String title, String category, LocalDateTime date,
                  List<com.example.recipe.entity.Recipe.Ingredient> ingredients, List<com.example.recipe.entity.Recipe.Step> steps, String description) {
        this.recipeId = recipeId;
        this.user = user;
        this.title = title;
        this.category = category;
        this.date = date;
        this.ingredients = ingredients;
        this.steps = steps;
        this.description = description;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ingredient {
        private String name;
        private String quantity;
    }

    @Embeddable
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Step {
        private String description;

        @Lob
        @Column(columnDefinition = "BLOB")
        private byte[] photo; // LOB 방식으로 이미지 저장
    }
}
