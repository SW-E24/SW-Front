package com.example.recipe;

import com.example.recipe.entity.*;
import com.example.recipe.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RecipeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private LikeRepository likeRepository;
    @MockBean
    private ViewRepository viewRepository;
    @MockBean
    private BookmarkRepository bookmarkRepository;
    @MockBean
    private CommentRepository commentRepository;

    private User testUser;
    private Recipe testRecipe;
    private Like testLike;
    private Bookmark testBookmark;
    private Comment testComment;
    private View testView;

    @BeforeEach
    void setUp() {
        // Initialize test user
        testUser = new User();
        testUser.setUserId("testUser123");
        testUser.setEmail("test@example.com");
        testUser.setUserName("Test User");
        testUser.setPassword("password123");
        testUser.setGrade("REGULAR");

        // Initialize test recipe
        testRecipe = new Recipe();
        testRecipe.setRecipeId(1L);
        testRecipe.setTitle("Test Recipe");
        testRecipe.setUser(testUser);
        testRecipe.setDescription("Test Description");
//        testRecipe.setIngredients("Test Ingredients");
        testRecipe.setDate(LocalDateTime.now());

        // Initialize test like
        testLike = new Like();
        testLike.setUser(testUser);
        testLike.setRecipe(testRecipe);

        // Initialize test bookmark
        testBookmark = new Bookmark();
        testBookmark.setUser(testUser);
        testBookmark.setRecipe(testRecipe);
        testBookmark.setDate(LocalDateTime.now());

        // Initialize test comment
        testComment = new Comment();
        testComment.setUserId(testUser.getUserId());
        testComment.setRecipeId(testRecipe.getRecipeId());
        testComment.setContent("Test Comment");
        testComment.setDate(LocalDateTime.now());

        // Initialize test view
        testView = new View();
        testView.setRecipe(testRecipe);
        testView.setCountView(1);

        // Setup mock repository responses
        setupMockRepositories();
    }

    private void setupMockRepositories() {
        Mockito.when(userRepository.findById(testUser.getUserId())).thenReturn(Optional.of(testUser));
        Mockito.when(recipeRepository.findById(testRecipe.getRecipeId())).thenReturn(Optional.of(testRecipe));
        Mockito.when(likeRepository.save(any(Like.class))).thenReturn(testLike);
        Mockito.when(bookmarkRepository.save(any(Bookmark.class))).thenReturn(testBookmark);
        Mockito.when(viewRepository.save(any(View.class))).thenReturn(testView);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(testUser);
        Mockito.when(recipeRepository.findAllByUserUserId(testUser.getUserId())).thenReturn(Arrays.asList(testRecipe));
        Mockito.when(commentRepository.findAllByUserId(testUser.getUserId())).thenReturn(Arrays.asList(testComment));
        Mockito.when(bookmarkRepository.findAllByUserUserId(testUser.getUserId())).thenReturn(Arrays.asList(testBookmark));
    }

    @Test
    void testGetUserInfo() throws Exception {
        mockMvc.perform(get("/api/users/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(testUser.getUserId()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()))
                .andExpect(jsonPath("$.userName").value(testUser.getUserName()));
    }

    @Test
    void testUpdateUserInfo() throws Exception {
        User updatedUser = new User();
        updatedUser.setUserName("Updated Name");
        updatedUser.setEmail("updated@example.com");

        mockMvc.perform(put("/api/users/{userId}", testUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    void testAddLike() throws Exception {
        mockMvc.perform(post("/api/likes")
                        .param("userId", testUser.getUserId())
                        .param("recipeId", testRecipe.getRecipeId().toString()))
                .andExpect(status().isCreated());
    }

    @Test
    void testRemoveLike() throws Exception {
        Mockito.when(likeRepository.findByUserUserIdAndRecipeRecipeId(
                        testUser.getUserId(), testRecipe.getRecipeId()))
                .thenReturn(Optional.of(testLike));

        mockMvc.perform(delete("/api/likes")
                        .param("userId", testUser.getUserId())
                        .param("recipeId", testRecipe.getRecipeId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testAddBookmark() throws Exception {
        mockMvc.perform(post("/api/bookmarks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBookmark)))
                .andExpect(status().isCreated());
    }

    @Test
    void testRemoveBookmark() throws Exception {
        Mockito.when(bookmarkRepository.findByUserUserIdAndRecipeRecipeId(
                        testUser.getUserId(), testRecipe.getRecipeId()))
                .thenReturn(Optional.of(testBookmark));

        mockMvc.perform(delete("/api/bookmarks/{userId}/{recipeId}",
                        testUser.getUserId(), testRecipe.getRecipeId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetUserRecipes() throws Exception {
        mockMvc.perform(get("/api/recipes/user/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].title").value(testRecipe.getTitle()));
    }

    @Test
    void testGetUserComments() throws Exception {
        mockMvc.perform(get("/api/comments/user/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].content").value(testComment.getContent()));
    }

    @Test
    void testGetUserBookmarks() throws Exception {
        mockMvc.perform(get("/api/bookmarks/{userId}", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testIncrementViewCount() throws Exception {
        Mockito.when(viewRepository.findByRecipeRecipeId(testRecipe.getRecipeId()))
                .thenReturn(Optional.of(testView));

        mockMvc.perform(post("/api/views/{recipeId}", testRecipe.getRecipeId()))
                .andExpect(status().isOk())
                .andExpect(content().string("조회수가 증가했습니다."));
    }

    @Test
    void testGetUserLevel() throws Exception {
        mockMvc.perform(get("/api/users/{userId}/grade", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(content().string(testUser.getGrade()));
    }

    @Test
    void testGetMyPageInfo() throws Exception {
        mockMvc.perform(get("/api/users/{userId}/mypage", testUser.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(testUser.getUserId()))
                .andExpect(jsonPath("$.userName").value(testUser.getUserName()))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));
    }
}