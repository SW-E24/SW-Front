package com.example.recipe.Controller;

import com.example.recipe.Entity.Bookmark;
import com.example.recipe.Service.BookmarkService;
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

    @PostMapping
    public ResponseEntity<Bookmark> addBookmark(@RequestBody Bookmark bookmark) {
        Bookmark newBookmark = bookmarkService.addBookmark(bookmark);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBookmark);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Bookmark>> getBookmarks(@PathVariable String userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }
}
