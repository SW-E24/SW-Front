package com.example.recipe.controller;

import com.example.recipe.entity.Bookmark;
import com.example.recipe.entity.Member;
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

    /*
    // 토글 형식이기 때문에 (해당사용자아이디, 해당게시글아이디) 로 등록된 북마크가 있는지 조회 후 -> 상태에 따라 로직 실행하는 방법
    @PostMapping
    public ResponseEntity<Bookmark> toggleBookmark(@RequestParam String userId, @RequestParam Long recipeId) {
        // 현재사용자아이디, 현재보고있는게시글아이디 로 등록된 북마크가 있는지 조회
        boolean existingBookmark = bookmarkRepository.existsByUserUserIdAndRecipeRecipeId(userId, recipeId);

        if (!existingBookmark) {    // 기존 북마크 한 거 존재
            bookmarkService.removeBookmark(userId, recipeId);
            return ResponseEntity.noContent().build();  // 삭제 성공 응답
        } else {    // 북마크가 없으면 추가
            Bookmark newBookmark = new Bookmark();
            newBookmark.setUser(get);
        }
    }
     */

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
