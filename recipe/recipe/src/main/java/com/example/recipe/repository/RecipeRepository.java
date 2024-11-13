package com.example.recipe.repository;

import com.example.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // 검색 기능을 위한 메소드
    List<Recipe> findByTitleContaining(String keyword);

    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name = :ingredientName")
    List<Recipe> findByIngredientName(@Param("ingredientName") String ingredientName);

    // 카테고리 기능을 위한 메소드
    List<Recipe> findByCategory(String category);

    // 메뉴추천을 위해 랜덤한 레시피를 가져오는 쿼리를 가진 메소드
    @Query(value = "SELECT * FROM Recipe ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Recipe findRandomRecipe();
}
