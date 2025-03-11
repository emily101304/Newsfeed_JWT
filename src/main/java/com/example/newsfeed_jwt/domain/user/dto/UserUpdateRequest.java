package com.example.newsfeed_jwt.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String name;
    private String image;
    private String phoneNumber;
    private LocalDate birthday;
}
