package com.example.recipe.Repository;

import com.example.recipe.Entity.Like;
import com.example.recipe.Entity.Recipe;
import com.example.recipe.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUserUserIdAndRecipeRecipeId(String userId, Long recipeId);
    Optional<Like> findByUserUserIdAndRecipeRecipeId(String userId, Long recipeId);
    Optional<Like> findByUserAndRecipe(User user, Recipe recipe);
}
