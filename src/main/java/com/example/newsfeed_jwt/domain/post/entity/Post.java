package com.example.newsfeed_jwt.domain.post.entity;

import com.example.newsfeed_jwt.common.entity.BaseEntity;
import com.example.newsfeed_jwt.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, String image, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.user = user;
    }

    public static Post of(String title, String content, String image, User user) {
        return new Post(title,content,image,user);
    }

    public void update(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
