package com.example.newsfeed_jwt.domain.user.repository;

import com.example.newsfeed_jwt.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
