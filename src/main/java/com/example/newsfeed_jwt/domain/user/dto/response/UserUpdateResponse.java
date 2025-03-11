package com.example.newsfeed_jwt.domain.user.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserUpdateResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String image;
    private final LocalDate birthday;
    private final String phoneNumber;

    public UserUpdateResponse(
            Long id,
            String name,
            String email,
            String image,
            LocalDate birthday,
            String phoneNumber
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public static UserUpdateResponse of(
            Long id,
            String name,
            String email,
            String image,
            LocalDate birthday,
            String phoneNumber
    ) {
        return new UserUpdateResponse(id, name, email, image, birthday, phoneNumber);
    }
}
