package com.example.recipe.controller;

import com.example.recipe.entity.Comment;
import com.example.recipe.entity.Like;
import com.example.recipe.entity.Recipe;
import com.example.recipe.service.CommentService;
import com.example.recipe.service.LikeService;
import com.example.recipe.service.RecipeService;
import org.springframework.ui.Model;
import com.example.recipe.entity.Bookmark;
import com.example.recipe.service.BookmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//마이페이지 이동 컨트롤러 새로 생성
@Controller
@RequestMapping("/mypage")
public class MypagePageController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/{userId}")
    public String showBookmarks(@PathVariable String userId, Model model) {
        // 북마크 데이터를 서비스에서 가져오기
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserID(userId);
        // 모델에 북마크 데이터 추가
        model.addAttribute("bookmarks", bookmarks);
        // saved.html 템플릿 렌더링
        return "saved"; // resources/templates/saved.html
    }

    // 사용자의 레시피 조회 화면
    @GetMapping("/{userId}/recipes")
    public String showUserRecipes(@PathVariable String userId, Model model) {
        List<Recipe> recipes = recipeService.getRecipesByUserId(userId);
        model.addAttribute("recipes", recipes);
        return "mypost";
    }

    @GetMapping("/{userId}/likes")
    public String showUserLikes(@PathVariable String userId, Model model) {
        // 사용자의 좋아요 데이터를 가져오기
        List<Like> likes = likeService.getLikesByUserId(userId);
        // 좋아요 데이터를 모델에 추가
        model.addAttribute("likes", likes);
        return "liked";
    }

    // 사용자의 댓글 조회 화면
    @GetMapping("/{userId}/comments")
    public String showUserComments(@PathVariable String userId, Model model) {
        List<Comment> comments = commentService.getCommentsByUserId(userId);
        model.addAttribute("comments", comments);
        return "mycomment"; // resources/templates/user-comments.html
    }

}
