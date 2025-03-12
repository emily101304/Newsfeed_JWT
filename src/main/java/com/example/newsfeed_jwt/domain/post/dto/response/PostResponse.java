package com.example.newsfeed_jwt.domain.post.dto.response;

import com.example.newsfeed_jwt.domain.post.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String image;
    private final Long userId;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private final LocalDateTime modifiedAt;


    public PostResponse(
            Long id,
            String title,
            String content,
            String image,
            Long userId,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.userId = userId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static PostResponse of(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getModifiedAt()
        );
    }
}
