package com.example.recipe.Repository;

import com.example.recipe.Entity.View;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ViewRepository extends JpaRepository<View, Long> {
    Optional<View> findByRecipeRecipeId(Long recipeId);
}