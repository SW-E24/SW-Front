package com.example.recipe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.recipe.Entity.Bookmark;
import com.example.recipe.Service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @Test
    public void testAddBookmark() throws Exception {
        // Given
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkId(1L);

        // When
        when(bookmarkService.addBookmark(any(Bookmark.class))).thenReturn(bookmark);

        // Then
        mockMvc.perform(post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":\"testUser\",\"recipeId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookmarkId").value(1L));
    }

    @Test
    public void testGetBookmarksByUserId() throws Exception {
        // Given: 가짜 북마크 리스트를 생성
        List<Bookmark> bookmarks = new ArrayList<>();
        // 테스트 북마크 객체 생성 (필요에 따라 필드 채움)
        Bookmark bookmark = new Bookmark();
        bookmarks.add(bookmark);

        // When: 서비스 호출 시 String 타입의 userId 사용
        when(bookmarkService.getBookmarksByUserId("1")).thenReturn(bookmarks);  // Long -> String으로 변경

        // Then: MockMvc로 API 호출
        mockMvc.perform(get("/api/bookmarks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookmarkId").exists());
    }
}
