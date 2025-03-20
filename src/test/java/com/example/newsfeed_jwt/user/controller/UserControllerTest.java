package com.example.newsfeed_jwt.user.controller;

import com.example.newsfeed_jwt.domain.user.controller.UserController;
import com.example.newsfeed_jwt.domain.user.dto.response.UserResponse;
import com.example.newsfeed_jwt.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void User_목록_조회_빈_리스트() throws Exception {
        //given
        String email = "asd@asd.com";
        given(userService.getAll()).willReturn(List.of());

        //when & then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void User_목록_조회() throws Exception {
        //given
        long userId1 = 1L;
        long userId2 = 2L;
        String email1 = "asd@asd.com";
        String email2 = "qwe@qwe.com";
        List<UserResponse> userList = List.of(
                new UserResponse(userId1,"name", email1, "image"),
                new UserResponse(userId2, "name2", email2, "image2")
        );
        given(userService.getAll()).willReturn(userList);

        //when & then
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(userId1))
                .andExpect(jsonPath("$[0].email").value(email1))
                .andExpect(jsonPath("$[1].id").value(userId2))
                .andExpect(jsonPath("$[1].email").value(email2));
    }
}
