package com.example.recipe.Service;

import com.example.recipe.Entity.Like;
import com.example.recipe.Repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    //좋아요 누르기
    public Like addLike(Like like) {
        return likeRepository.save(like);
    }
}
