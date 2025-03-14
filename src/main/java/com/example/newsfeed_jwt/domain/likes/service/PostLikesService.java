package com.example.newsfeed_jwt.domain.likes.service;

import com.example.newsfeed_jwt.domain.likes.entity.PostLikes;
import com.example.newsfeed_jwt.domain.likes.repository.PostLikesRepository;
import com.example.newsfeed_jwt.domain.post.entity.Post;
import com.example.newsfeed_jwt.domain.post.repository.PostRepository;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikesService extends LikesService<PostLikes> {

    private final PostRepository postRepository;
    private final PostLikesRepository postLikesRepository;
    private final UserRepository userRepository;

    public PostLikesService(
            PostLikesRepository postLikesRepository,
            UserRepository userRepository,
            PostRepository postRepository
    ) {
        super(postLikesRepository, userRepository);
        this.postRepository = postRepository;
        this.postLikesRepository = postLikesRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addPostLike(Long postId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글 입니다.")
        );

        addLike(post.getUser().getId(),postId, new PostLikes(user,post));
    }

    @Transactional
    public void cancelPostLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글 입니다.")
        );
        cancelLike(post.getId(),userId);
    }
}
