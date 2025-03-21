package com.example.newsfeed_jwt.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignUpResponse {

    private final String bearerJwt;

    public SignUpResponse(String bearerJwt) {
        this.bearerJwt = bearerJwt;
    }
}
