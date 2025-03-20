package com.example.newsfeed_jwt.domain.user.service;

import com.example.newsfeed_jwt.config.PasswordEncoder;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.auth.dto.request.SignInRequest;
import com.example.newsfeed_jwt.domain.auth.dto.request.SignUpRequest;
import com.example.newsfeed_jwt.domain.user.dto.request.UpdatePasswordRequest;
import com.example.newsfeed_jwt.domain.user.dto.response.UserResponse;
import com.example.newsfeed_jwt.domain.user.dto.request.UserUpdateRequest;
import com.example.newsfeed_jwt.domain.user.dto.response.UserUpdateResponse;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
////    private final PasswordEncoder passwordEncoder;
//
////    @Transactional
////    public UserResponse save(SignUpRequest request) {
////
////        if (userRepository.existsByEmail(request.getEmail())) {
////            throw new IllegalStateException("이미 가입된 이메일 입니다.");
////        }
////
////        String encodedPassword = passwordEncoder.encode(request.getPassword());
////
////        User user = new User(
////                request.getName(),
////                request.getEmail(),
////                encodedPassword,
////                request.getImage(),
////                request.getBirthday(),
////                request.getPhoneNumber()
////        );
////        User savedUser = userRepository.save(user);
////        return UserResponse.of(savedUser);
//    }
//
////    @Transactional(readOnly = true)
////    public UserResponse findByEmail(SignInRequest request) {
////        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
////                () -> new IllegalStateException("해당 유저는 존재하지 않습니다.")
////        );
////        if (!passwordEncoder.matches(user.getPassword(), request.getPassword())) {
////            throw new IllegalStateException("입력하신 비밀번호가 일치하지 않습니다.");
////        }
////        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getImage());
////    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<UserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new UserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getImage()
            ));
        }
        return dtos;
    }

    @Transactional
    public UserUpdateResponse update(AuthUser authUser, UserUpdateRequest dto) {
        User user = findUser(authUser);

        user.update(dto.getName(),dto.getImage(),dto.getBirthday(), dto.getPhoneNumber());
        return new UserUpdateResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getImage(),
                user.getBirthday(),
                user.getPhoneNumber());
    }

    @Transactional
    public void updatePassword(AuthUser authUser, UpdatePasswordRequest dto) {
        User user = findUser(authUser);
        // 비밀번호 불일치
        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        // 이전 비밀번호와 바꾸려는 비밀번호가 동일
        if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
            throw new IllegalStateException("이전에 사용한 비밀번호로는 변경할 수 없습니다.");
        }
        user.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    public User findUser(AuthUser authUser) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new IllegalStateException("해당 유저는 존재하지 않습니다.")
        );
        return user;
    }
}
