package com.example.recipe.Service;

import com.example.recipe.Entity.User;
import com.example.recipe.Repository.UserRepository;
import com.example.recipe.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //회원정보 가져오기
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    //회원정보 수정
    public User updateUser(String userId, User updatedUser) {
        User user = getUserById(userId);
        user.setUserName(updatedUser.getUserName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());
        return userRepository.save(user);
    }

    //마이페이지 작성글 가져오기

    //마이페이지 작성댓글 가져오기
}
