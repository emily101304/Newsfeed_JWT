package com.example.newsfeed_jwt.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    @Size(min = 8, message = "비밀번호는 8자리 이상으로 작성해주세요.")
    private String newPassword;
}
