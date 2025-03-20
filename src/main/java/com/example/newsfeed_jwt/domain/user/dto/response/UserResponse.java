package com.example.newsfeed_jwt.domain.user.dto.response;

import com.example.newsfeed_jwt.domain.user.entity.User;
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

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getImage()
        );
    }
}
