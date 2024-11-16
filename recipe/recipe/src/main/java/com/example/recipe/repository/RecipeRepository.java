//package com.example.recipe.repository;
//
//import com.example.recipe.entity.Recipe;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//@Repository
//public interface RecipeRepository extends JpaRepository<Recipe, Long> {
//
////    Object findAllByUserUserID(String userID);
//    List<Recipe> findAllByUserUserId(String userId);
//
//    // 검색 기능을 위한 메소드
//    List<Recipe> findByTitleContaining(String keyword);
//
//    // 재료 이름으로 레시피 찾기
//    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name = :ingredientName")
//    List<Recipe> findByIngredientName(@Param("ingredientName") String ingredientName);
//
//    // 카테고리별 레시피 찾기
//    List<Recipe> findByCategory(String category);
//
//    // 메뉴 추천을 위해 랜덤한 레시피 가져오기
//    @Query(value = "SELECT * FROM Recipe ORDER BY RAND() LIMIT 1", nativeQuery = true)
//    Recipe findRandomRecipe();
//}

//전체 코드 수정, 위 코드는 혹시나해서 남겨둠
package com.example.recipe.repository;

import com.example.recipe.entity.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    //    Object findAllByUserUserID(String userID);
    List<Recipe> findAllByUserUserId(String userId);

    // 제목으로 레시피 찾기
    Page<Recipe> findByTitleContaining(String keyword, Pageable pageable);

    // 재료 이름으로 레시피 찾기
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name LIKE %:ingredient%")
    Page<Recipe> findByIngredientContaining(@Param("ingredient") String ingredient, Pageable pageable);

    // 카테고리별 레시피 찾기
    List<Recipe> findByCategory(String category);

}