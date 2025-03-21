package com.example.newsfeed_jwt.domain.auth.controller;

import com.example.newsfeed_jwt.domain.auth.dto.request.SignInRequest;
import com.example.newsfeed_jwt.domain.auth.dto.request.SignUpRequest;
import com.example.newsfeed_jwt.domain.auth.dto.response.SignInResponse;
import com.example.newsfeed_jwt.domain.auth.dto.response.SignUpResponse;
import com.example.newsfeed_jwt.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/api/v1/auth/signup")
//    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest request) {
//        return authService.signUp(request);
//    }
//
//    @PostMapping("/api/v1/auth/signin")
//    public SignInResponse singIn(@Valid @RequestBody SignInRequest request) {
//        return authService.signIn(request);
//    }

    @PostMapping("/api/v1/auth/signup")
    public String signUp(@Valid @RequestBody SignUpRequest request) {
        System.out.println("start");
        authService.signUp(request);
        return "회원가입 완료";
    }

    @PostMapping("/api/v1/auth/signin")
    public SignInResponse signIn(@Valid @RequestBody SignInRequest request) {
        return authService.signIn(request);
    }
}
