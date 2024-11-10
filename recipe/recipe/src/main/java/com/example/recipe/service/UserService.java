package com.example.recipe.service;

import com.example.recipe.entity.User;
import com.example.recipe.repository.UserRepository;
import com.example.recipe.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    public User getMyProfile(String userId) {
//        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//    }

    public User getUserById(String userId) { //사용자 조회 메소드
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public User updateUser(String userId, User updatedUser) { //유저 정보 업데이트 메소드
        User user = getUserById(userId);
        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        return userRepository.save(user);
    }

    public User getMyPageInfo(String userId) { //내 정보 보기
        return getUserById(userId); // 기존 메서드 활용
    }

    public String getUserLevel(String userId) { //현재 등급 확인
        User user = getUserById(userId);
        return user.getGrade(); // 'Grade' 필드가 있다고 가정 (등급)
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}

