package com.example.newsfeed_jwt.domain.likes.service;

import com.example.newsfeed_jwt.domain.comment.entity.Comment;
import com.example.newsfeed_jwt.domain.comment.repository.CommentRepository;
import com.example.newsfeed_jwt.domain.likes.entity.CommentLikes;
import com.example.newsfeed_jwt.domain.likes.repository.CommentLikesRepository;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentLikesService extends LikesService<CommentLikes> {

    private final CommentRepository commentRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final UserRepository userRepository;

    public CommentLikesService(
            CommentLikesRepository commentLikesRepository,
            CommentRepository commentRepository,
            UserRepository userRepository
            ) {
        super(commentLikesRepository, userRepository);
        this.commentRepository = commentRepository;
        this.commentLikesRepository = commentLikesRepository;
        this.userRepository = userRepository;
    }

    public void addCommentLike(Long commentId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저 입니다.")
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글 입니다.")
        );
        addLike(comment.getUserId(), comment.getId(), new CommentLikes(user, comment));
    }

    public void cancelCommentLike(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 댓글 입니다.")
        );
        cancelLike(comment.getId(),userId);
    }
}
