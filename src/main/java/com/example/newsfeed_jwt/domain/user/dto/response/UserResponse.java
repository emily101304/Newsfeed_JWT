package com.example.newsfeed_jwt.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String image;

    public UserResponse(Long id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }
}
