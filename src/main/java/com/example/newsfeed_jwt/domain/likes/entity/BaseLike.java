package com.example.newsfeed_jwt.domain.likes.entity;

import com.example.newsfeed_jwt.common.entity.BaseEntity;
import com.example.newsfeed_jwt.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseLike extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_Id", nullable = false)
    private User user;

    public BaseLike(User user) {
        this.user = user;
    }
}
