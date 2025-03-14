package com.example.newsfeed_jwt.domain.comment.repository;

import com.example.newsfeed_jwt.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(@Param("postId") Long postId);
}
