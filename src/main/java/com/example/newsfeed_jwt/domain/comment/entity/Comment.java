package com.example.newsfeed_jwt.domain.comment.entity;

import com.example.newsfeed_jwt.common.entity.BaseEntity;
import com.example.newsfeed_jwt.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(nullable = false)
    private Long userId;

    public Comment(String content, Post post, Long userId) {
        this.content = content;
        this.post = post;
        this.userId = userId;
    }

    public void update(String content) {
        this.content = content;
    }
}
