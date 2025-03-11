package com.example.newsfeed_jwt.domain.user.controller;

import com.example.newsfeed_jwt.domain.user.dto.request.UpdatePasswordRequest;
import com.example.newsfeed_jwt.domain.user.dto.response.UserResponse;
import com.example.newsfeed_jwt.domain.user.dto.request.UserUpdateRequest;
import com.example.newsfeed_jwt.domain.user.dto.response.UserUpdateResponse;
import com.example.newsfeed_jwt.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    private List<UserResponse> getUsers() {
        return userService.getAll();
    }

    @PatchMapping("/api/v1/users/{userId}")
    private UserUpdateResponse updateMe(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return userService.update(userId, request);
    }

    @PatchMapping("/api/v1/users/{userId}")
    private void updatePassword(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        userService.updatePassword(userId, request);
    }
}
