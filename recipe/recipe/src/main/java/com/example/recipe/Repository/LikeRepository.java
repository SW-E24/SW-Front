package com.example.recipe.Repository;

import com.example.recipe.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {}