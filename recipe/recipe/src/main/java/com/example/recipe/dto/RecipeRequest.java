package com.example.recipe.dto;

import com.example.recipe.model.Recipe.Ingredient;

import java.util.List;

public class RecipeRequest {
    private String title;
    private String category;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private String description;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public List<Ingredient> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredient> ingredients) { this.ingredients = ingredients; }

    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
