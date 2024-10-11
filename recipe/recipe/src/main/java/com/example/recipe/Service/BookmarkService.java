package com.example.recipe.Service;

import com.example.recipe.Entity.Bookmark;
import com.example.recipe.Repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    //북마크 추가
    public Bookmark addBookmark(Bookmark bookmark) {
        bookmark.setDate(LocalDateTime.now());
        return bookmarkRepository.save(bookmark);
    }

    //북마크 읽어오기
    public List<Bookmark> getBookmarksByUserId(String userId) {
        return bookmarkRepository.findAllByUserUserId(userId);
    }
}
