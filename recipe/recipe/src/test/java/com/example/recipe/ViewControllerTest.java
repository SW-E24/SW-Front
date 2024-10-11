package com.example.recipe;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content; // 올바른 content() 메서드

import com.example.recipe.Service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ViewService viewService;

    @Test
    public void testIncrementViewCount() throws Exception {
        // When
        doNothing().when(viewService).incrementViewCount(1L);

        // Then
        mockMvc.perform(post("/api/views/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("조회수가 증가했습니다.")); // 올바른 content() 메서드 사용
    }
}
