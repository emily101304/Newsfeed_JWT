package com.example.newsfeed_jwt.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자리 이상으로 작성해주세요.")
    private String oldPassword;

    @NotBlank
    @Size(min = 8, message = "비밀번호는 8자리 이상으로 작성해주세요.")
    private String newPassword;
}
