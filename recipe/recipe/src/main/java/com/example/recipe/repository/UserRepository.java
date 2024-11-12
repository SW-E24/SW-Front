package com.example.recipe.repository;

import com.example.recipe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, String> {
}
