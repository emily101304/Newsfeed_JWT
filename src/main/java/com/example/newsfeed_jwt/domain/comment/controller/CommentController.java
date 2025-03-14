package com.example.newsfeed_jwt.domain.comment.controller;

import com.example.newsfeed_jwt.domain.auth.annotation.Auth;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.comment.dto.request.CommentRequest;
import com.example.newsfeed_jwt.domain.comment.dto.response.CommentResponse;
import com.example.newsfeed_jwt.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/posts/{postId}/comments")
    public CommentResponse save(
            @PathVariable Long postId,
            @Auth AuthUser authUser,
            @Valid @RequestBody CommentRequest request
    ) {
        return commentService.save(postId, authUser, request);
    }

    @GetMapping("/api/v1/posts/{postId}/comments")
    public List<CommentResponse> getAllComment(@PathVariable Long postId) {
        return commentService.getAll(postId);
    }

    @PutMapping("/api/v1/comments/{commentId}/updates")
    public CommentResponse updateComment(
            @PathVariable Long commentId,
            @Auth AuthUser authUser,
            @Valid @RequestBody CommentRequest request
    ) {
        return commentService.update(commentId, authUser, request);
    }

    @DeleteMapping("/api/v1/comments/{commentId}")
    public void deleteComment(
            @PathVariable Long commentId,
            @Auth AuthUser authUser
    ) {
        commentService.delete(commentId, authUser);
    }
}