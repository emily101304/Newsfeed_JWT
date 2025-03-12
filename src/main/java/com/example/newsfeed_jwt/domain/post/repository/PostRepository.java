package com.example.newsfeed_jwt.domain.post.repository;

import com.example.newsfeed_jwt.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
