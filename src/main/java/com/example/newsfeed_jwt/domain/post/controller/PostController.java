package com.example.newsfeed_jwt.domain.post.controller;

import com.example.newsfeed_jwt.domain.auth.annotation.Auth;
import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.post.dto.request.PostRequest;
import com.example.newsfeed_jwt.domain.post.dto.request.UpdatePostRequest;
import com.example.newsfeed_jwt.domain.post.dto.response.PostResponse;
import com.example.newsfeed_jwt.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/v1/posts")
    public PostResponse createPost(
            @Auth AuthUser authUser,
            @Valid @RequestBody PostRequest request) {
        return postService.createPost(authUser,request);
    }

    @GetMapping("/api/v1/posts")
    public Page<PostResponse> getAllPosts(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/api/v1/posts/{postId}")
    public PostResponse getOnePost(@PathVariable Long postId) {
        return postService.getOnePost(postId);
    }

    @PatchMapping("/api/v1/posts/{postId}")
    public PostResponse updatePost(
            @PathVariable Long postId,
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdatePostRequest request
    ) {
        return postService.update(postId,authUser,request);
    }

    @DeleteMapping("/api/v1/posts/{postId}")
    public void deletePost(
            @PathVariable Long postId,
            @Auth AuthUser authUser
    ) {
        postService.delete(postId, authUser);
    }
}