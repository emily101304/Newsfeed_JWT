package com.example.newsfeed_jwt.domain.post.service;

import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.follow.repository.FollowRepository;
import com.example.newsfeed_jwt.domain.post.dto.request.PostRequest;
import com.example.newsfeed_jwt.domain.post.dto.request.UpdatePostRequest;
import com.example.newsfeed_jwt.domain.post.dto.response.PostResponse;
import com.example.newsfeed_jwt.domain.post.entity.Post;
import com.example.newsfeed_jwt.domain.post.repository.PostRepository;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest request) {
        User user = userRepository.findById(authUser.getUserId()).orElseThrow(
                () -> new IllegalStateException("해당 유저는 존재하지 않습니다.")
        );

        Post post = Post.of(request.getTitle(), request.getContent(), request.getImage(),user);
        Post saved = postRepository.save(post);

        return PostResponse.of(saved);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(post -> new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getModifiedAt()
        ));
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllMyFollowerPosts(Long userId) {
        // 내가 팔로우한 사용자들 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("해당 유저는 존재하지 않습니다."));
        List<User> followedUsers = followRepository.findFollowedUsersByFollower(user);
        // 내가 팔로우한 사람들의 게시물을 최신순으로 조회
        List<Post> followerPosts = postRepository.findByUserInOrderByCreatedAtDesc(followedUsers);
        List<PostResponse> dtoList = new ArrayList<>();
        for (Post followerPost : followerPosts) {
            dtoList.add(new PostResponse(
                    followerPost.getId(),
                    followerPost.getTitle(),
                    followerPost.getContent(),
                    followerPost.getImage(),
                    followerPost.getUser().getId(),
                    followerPost.getCreatedAt(),
                    followerPost.getModifiedAt()));
        }
        return dtoList;
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        Page<Post> posts = postRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return posts.map(post -> new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImage(),
                post.getUser().getId(),
                post.getCreatedAt(),
                post.getModifiedAt()
        ));
    }

    @Transactional
    public PostResponse getOnePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글은 존재하지 않습니다.")
        );
        return PostResponse.of(post);
    }

    @Transactional
    public PostResponse update(Long postId, AuthUser authUser, UpdatePostRequest request) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글은 존재하지 않습니다.")
        );

        if (!post.getUser().getId().equals(authUser.getUserId())) {
            throw new IllegalStateException("해당 게시글을 작성한 유저가 아님으로 수정 불가합니다.");
        }

        post.update(request.getTitle(),request.getContent(),request.getImage());
        return PostResponse.of(post);
    }

    @Transactional
    public void delete(Long postId, AuthUser authUser) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("해당 게시글은 존재하지 않습니다.")
        );

        if (!post.getUser().getId().equals(authUser.getUserId())) {
            throw new IllegalStateException("해당 게시글을 작성한 사람이 아님으로 삭제 불가합니다.");
        }
        postRepository.deleteById(postId);
    }
}