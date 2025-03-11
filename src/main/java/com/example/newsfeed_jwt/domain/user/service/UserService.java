package com.example.newsfeed_jwt.domain.user.service;

import com.example.newsfeed_jwt.domain.user.dto.UpdatePasswordRequest;
import com.example.newsfeed_jwt.domain.user.dto.UserResponse;
import com.example.newsfeed_jwt.domain.user.dto.UserUpdateRequest;
import com.example.newsfeed_jwt.domain.user.dto.UserUpdateResponse;
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

    public UserUpdateResponse update(Long userId, UserUpdateRequest dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 존재하지 않습니다.")
        );

        user.update(dto.getName(),dto.getImage(),dto.getBirthday());
        return new UserUpdateResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getImage(),
                user.getBirthday(),
                user.getPhoneNumber());
    }

    public void updatePassword(Long userId, UpdatePasswordRequest dto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저가 존재하지 않습니다.")
        );
        checkPassword(user.getPassword(),dto.getNewPassword());
        changePassword(user.getPassword(),dto.getNewPassword());
        user.updatePassword(dto.getNewPassword());
    }

    private void checkPassword(String password, String newPassword) {
        if (!password.equals(newPassword)) {
            throw new IllegalStateException("입력하신 비밀번호가 일치하지 않습니다.");
        }
    }

    private void changePassword(String password, String newPassword) {
        if (password.equals(newPassword)) {
            throw new IllegalArgumentException("이전에 사용한 비밀번호로는 변경할 수 없습니다.");
        }
    }
}
