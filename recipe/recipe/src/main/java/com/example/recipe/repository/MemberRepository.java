package com.example.recipe.repository;

import com.example.recipe.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByUserId(String userId);
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
