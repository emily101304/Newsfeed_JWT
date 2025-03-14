package com.example.newsfeed_jwt.domain.likes.entity;

import com.example.newsfeed_jwt.domain.comment.entity.Comment;
import com.example.newsfeed_jwt.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comment_likes")
public class CommentLikes extends BaseLike {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    public CommentLikes(User user, Comment comment) {
        super(user);
        this.comment = comment;
    }
}
