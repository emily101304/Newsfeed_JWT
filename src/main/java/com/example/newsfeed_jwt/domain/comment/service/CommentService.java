package com.example.newsfeed_jwt.domain.comment.service;

import com.example.newsfeed_jwt.domain.auth.dto.AuthUser;
import com.example.newsfeed_jwt.domain.comment.dto.request.CommentRequest;
import com.example.newsfeed_jwt.domain.comment.dto.response.CommentResponse;
import com.example.newsfeed_jwt.domain.comment.entity.Comment;
import com.example.newsfeed_jwt.domain.comment.repository.CommentRepository;
import com.example.newsfeed_jwt.domain.post.entity.Post;
import com.example.newsfeed_jwt.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse save(Long postId, AuthUser authUser, CommentRequest request) {
        Post post = findPostById(postId);

        Comment comment = new Comment(request.getContent(), post, authUser.getUserId());
        Comment savedComment = commentRepository.save(comment);

        return CommentResponse.of(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAll(Long postId) {
        Post post = findPostById(postId);

        //특정 게시글에 있는 모든 댓글 리스트 찾아오기
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(
                comment -> new CommentResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getUserId(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).toList();
    }

    @Transactional
    public CommentResponse update(
            Long commentId,
            AuthUser authUser,
            CommentRequest request
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );
        if (!comment.getUserId().equals(authUser.getUserId())) {
            throw new IllegalStateException("해당 댓글을 작성한 유저가 아닙니다.");
        }
        comment.update(request.getContent());
        return CommentResponse.of(comment);
    }

    @Transactional
    public void delete(Long commentId, AuthUser authUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글 입니다.")
        );
        if (!comment.getUserId().equals(authUser.getUserId())) {
            throw new IllegalStateException("해당 댓글을 작성한 유저가 아닙니다.");
        }
        commentRepository.deleteById(commentId);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 게시글 입니다.")
        );
    }
}
