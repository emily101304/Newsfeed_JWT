package com.example.newsfeed_jwt.domain.likes.controller;

import com.example.newsfeed_jwt.domain.auth.annotation.Auth;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.likes.service.CommentLikesService;
import com.example.newsfeed_jwt.domain.likes.service.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final PostLikesService postLikesService;
    private final CommentLikesService commentLikesService;

    @PostMapping("/api/v1/likes/{type}/{typeId}")
    public void addPostLike(@Auth AuthUser authUser, @PathVariable String type, @PathVariable Long typeId) {
        switch (type) {
            case "posts" -> postLikesService.addPostLike(typeId, authUser.getUserId());
            case "comments" -> commentLikesService.addCommentLike(typeId, authUser.getUserId());
            default -> throw new IllegalStateException("올바른 타입이 아닙니다.");
        }
    }

    @DeleteMapping("/api/v1/likes/{type}/{typeId}")
    public void cancelPostLike(@Auth AuthUser authUser, @PathVariable String type, @PathVariable Long typeId) {
        switch (type) {
            case "posts" -> postLikesService.cancelPostLike(typeId, authUser.getUserId());
            case "comments" -> commentLikesService.cancelCommentLike(typeId, authUser.getUserId());
            default -> throw new IllegalStateException("올바른 타입이 아닙니다.");
        }
    }
}
