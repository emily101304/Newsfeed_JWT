package com.example.newsfeed_jwt.domain.likes.entity;

import com.example.newsfeed_jwt.domain.post.entity.Post;
import com.example.newsfeed_jwt.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "post_likes")
public class PostLikes extends  BaseLike{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public PostLikes(User user, Post post) {
        super(user);
        this.post = post;
    }
}
