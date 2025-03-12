package com.example.newsfeed_jwt.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String image;

    @NotBlank
    private Long userId;
}
