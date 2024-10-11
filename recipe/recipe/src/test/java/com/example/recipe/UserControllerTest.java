package com.example.recipe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // 여기서 올바른 put() 메서드를 임포트
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.recipe.Entity.User;
import com.example.recipe.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testGetUserById() throws Exception {
        // Given
        User user = new User();
        user.setUserId("testUser");
        user.setUserName("Test Name");
        user.setEmail("test@example.com");

        // When
        when(userService.getUserById("testUser")).thenReturn(user);

        // Then
        mockMvc.perform(get("/api/users/testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("testUser"))
                .andExpect(jsonPath("$.userName").value("Test Name"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // Given
        User updatedUser = new User();
        updatedUser.setUserId("testUser");
        updatedUser.setUserName("Updated Name");
        updatedUser.setEmail("updated@example.com");

        // When
        when(userService.updateUser(eq("testUser"), any(User.class))).thenReturn(updatedUser);

        // Then
        mockMvc.perform(put("/api/users/testUser") // 여기서 올바른 put() 메서드 사용
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"Updated Name\",\"email\":\"updated@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }
}
