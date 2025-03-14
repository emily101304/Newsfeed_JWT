package com.example.newsfeed_jwt.domain.likes.repository;

import com.example.newsfeed_jwt.domain.likes.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    Optional<CommentLikes> findByCommentIdAndUserId(Long commentId, Long userId);
}
