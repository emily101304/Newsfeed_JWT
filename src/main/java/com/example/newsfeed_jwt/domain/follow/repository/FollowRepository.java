package com.example.newsfeed_jwt.domain.follow.repository;

import com.example.newsfeed_jwt.domain.follow.entity.Follow;
import com.example.newsfeed_jwt.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("select f.followee From Follow f where f.follower = :member")
    List<User> findFollowedUsersByFollower(@Param("user") User user);
}
