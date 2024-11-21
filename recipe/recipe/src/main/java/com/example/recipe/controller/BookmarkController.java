package com.example.recipe.controller;

import com.example.recipe.entity.Bookmark;
import com.example.recipe.repository.BookmarkRepository;
import com.example.recipe.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @PostMapping
    public ResponseEntity<Bookmark> addBookmark(@RequestBody Bookmark bookmark) {
        Bookmark newBookmark = bookmarkService.addBookmark(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBookmark);
    }

    // 파라미터로 사용자아이디, 게시글 아이디 주어서 저장하는 방법
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addBookmark(@RequestParam String userId, @RequestParam Long recipeId) {
//        // 이미 북마크가 눌러져있는지 확인
//        boolean existsBookmark = bookmarkRepository.existsByUserUserIdAndRecipeRecipeId(userId, recipeId);
//        if(!existsBookmark) {
//            bookmarkService.addBookmark(userId, recipeId);
//        }
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Bookmark>> getBookmarks(@PathVariable String userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserID(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @DeleteMapping("/{userId}/{recipeId}")
    public ResponseEntity<Void> removeBookmark(@PathVariable String userId, @PathVariable Long recipeId) {
        bookmarkService.removeBookmark(userId, recipeId);  // 북마크 취소 메소드 추가
        return ResponseEntity.noContent().build();
    }
}
