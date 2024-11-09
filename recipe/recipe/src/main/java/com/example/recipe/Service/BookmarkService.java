package com.example.recipe.Service;

import com.example.recipe.Entity.Bookmark;
import com.example.recipe.Entity.Recipe;
import com.example.recipe.Entity.User;
import com.example.recipe.Repository.BookmarkRepository;
import com.example.recipe.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    public Bookmark addBookmark(Bookmark bookmark) { //북마크 추가 메소드
        bookmark.setDate(LocalDateTime.now());
        return bookmarkRepository.save(bookmark);
    }

    public List<Bookmark> getBookmarksByUserId(String userId) { //모든 북마크를 가져오는 메소드
        return bookmarkRepository.findAllByUserUserId(userId);
    }

    public void removeBookmark(String userId, Long recipeId) { // 북마크 취소 메소드 추가
        Bookmark bookmark = bookmarkRepository.findByUserUserIdAndRecipeRecipeId(userId, recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Bookmark not found for userId: " + userId + " and recipeId: " + recipeId));

        bookmarkRepository.delete(bookmark);
    }
}
