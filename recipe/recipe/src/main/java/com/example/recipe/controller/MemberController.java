package com.example.recipe.controller;

import com.example.recipe.entity.Grade;
import com.example.recipe.entity.Member;
import com.example.recipe.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable String userId,
            @RequestParam("file") MultipartFile file) {
        try {
            memberService.updateProfilePicture(userId, file);
            return ResponseEntity.ok("프로필 사진이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("프로필 사진 업데이트에 실패했습니다.");
        }
    }

    @GetMapping("/{userId}/profile-picture")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable String userId) {
        Member member = memberService.getMemberById(userId);
        byte[] profileImage = member.getProfileImage();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(profileImage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Member> getUser(@PathVariable String userId) {
        Member user = memberService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody Member updatedUser) {
        try {
            // 기존 유저인지 확인
            String userId = updatedUser.getUserId();
            Member existingUser =memberService.findUserByUserID(userId);

            // 정보 업데이트
            existingUser.setUserId(userId);
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPhone(updatedUser.getPhone());
            existingUser.setPassword(updatedUser.getPassword());

            // 기존 이미지 값 보존
            if (updatedUser.getProfileImage() == null) {
                updatedUser.setProfileImage(existingUser.getProfileImage()); // 이미지 수정되지 않으면 기존 이미지 유지
            }

            memberService.updateUser(userId, existingUser);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 중 오류 발생(controller): " + e.getMessage());
        }

    }

    @GetMapping("/{userId}/mypage")
    public ResponseEntity<Member> getMyPageInfo(@PathVariable String userId) {
        Member user = memberService.getMyPageInfo(userId);  // 마이페이지 보기 - 내 정보 보기 메소드 추가
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}/grade")
    public ResponseEntity<Grade> getUserLevel(@PathVariable String userId) {
        Grade grade = memberService.getUserLevel(userId);  // 현재 등급 확인 메소드 추가
        return ResponseEntity.ok(grade);
    }

//    @PostMapping("/{userId}/updateProfile")
//    public ResponseEntity<Member> updateProfile(@RequestBody Member updatedMember, @RequestParam String userId) {
//        try {
//            // 업데이트된 정보를 저장
//            Member updatedUser = memberService.updateUser(userId, updatedMember);
//            return ResponseEntity.ok(updatedUser); // 업데이트된 정보를 반환
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 실패 시 오류 반환
//        }
//    }
}
