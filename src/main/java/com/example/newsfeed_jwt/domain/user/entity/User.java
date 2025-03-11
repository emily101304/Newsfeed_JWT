package com.example.newsfeed_jwt.domain.user.entity;

import com.example.newsfeed_jwt.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String image;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    public User(String name, String email, String password, String image, LocalDate birthday, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
    }

    public void update(String name, String image, LocalDate birthday) {
        this.name = name;
        this.image = image;
        this.birthday = birthday;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
