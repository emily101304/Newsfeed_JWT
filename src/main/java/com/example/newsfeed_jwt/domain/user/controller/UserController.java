package com.example.newsfeed_jwt.domain.user.controller;

import com.example.newsfeed_jwt.domain.auth.annotation.Auth;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
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

    @PatchMapping("/api/v1/users/{userId}/me")
    private UserUpdateResponse updateMe(
            @Auth AuthUser authUser,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        return userService.update(authUser, request);
    }

    @PatchMapping("/api/v1/users/{userId}/passwords")
    private void updatePassword(
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        userService.updatePassword(authUser, request);
    }
}
