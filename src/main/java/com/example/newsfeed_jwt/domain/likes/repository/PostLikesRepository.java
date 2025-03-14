package com.example.newsfeed_jwt.domain.likes.repository;

import com.example.newsfeed_jwt.domain.likes.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    Optional<PostLikes> findByPostIdAndUserId(Long postId, Long userId);
}
