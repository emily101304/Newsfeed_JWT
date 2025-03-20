package com.example.newsfeed_jwt.domain.auth.service;

import com.example.newsfeed_jwt.config.JwtUtil;
import com.example.newsfeed_jwt.config.PasswordEncoder;
import com.example.newsfeed_jwt.domain.auth.dto.request.SignInRequest;
import com.example.newsfeed_jwt.domain.auth.dto.request.SignUpRequest;
import com.example.newsfeed_jwt.domain.auth.dto.response.SignInResponse;
import com.example.newsfeed_jwt.domain.auth.dto.response.SignUpResponse;
import com.example.newsfeed_jwt.domain.user.dto.response.UserResponse;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import com.example.newsfeed_jwt.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

//    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

//    @Transactional
//    public SignUpResponse signUp(SignUpRequest request) {
//        UserResponse savedResult = userService.save(request);
//        String bearerJwt = jwtUtil.createToken(savedResult.getId(), savedResult.getEmail());
//        return new SignUpResponse(bearerJwt);
//    }

//    @Transactional
//    public SignUpResponse signUp(SignUpRequest request) {
//        UserResponse savedResult = userService.save(request);
//        String encodedPassword = passwordEncoder.encode(request.getPassword());
//        String bearerJwt = jwtUtil.createToken()
//    }
//
//    @Transactional(readOnly = true)
//    public SignInResponse signIn(SignInRequest request) {
//        UserResponse userResult = userService.findByEmail(request);
//        String bearerJwt = jwtUtil.createToken(userResult.getId(), userResult.getEmail());
//        return new SignInResponse(bearerJwt);
//    }

    @Transactional
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }
        // 회원가입 가능한 이메일
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword,
                request.getImage(),
                request.getBirthday(),
                request.getPhoneNumber());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        String password = request.getPassword();
        if (!password.equals(user.getPassword())) {
            throw new IllegalStateException("잘못된 비밀번호 입니다.");
        }

        String bearerJwt = jwtUtil.createToken(user.getId(), user.getEmail());
        return new SignInResponse(bearerJwt);
    }
}
