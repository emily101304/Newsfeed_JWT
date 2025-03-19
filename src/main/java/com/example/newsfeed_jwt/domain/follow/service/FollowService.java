package com.example.newsfeed_jwt.domain.follow.service;

import com.example.newsfeed_jwt.domain.follow.entity.Follow;
import com.example.newsfeed_jwt.domain.follow.repository.FollowRepository;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public String follow(Long followerId, Long followeeId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalStateException("Follower를 찾을 수 없습니다.")
        );
        User followee = userRepository.findById(followeeId).orElseThrow(
                () -> new IllegalStateException("Followee를 찾을 수 없습니다.")
        );

        Follow follow = new Follow(follower,followee);
        followRepository.save(follow);
        return "OK";
    }

    @Transactional(readOnly = true)
    public List<User> followers(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );
        return user.getFollowers().stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<User> followees(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("해당 유저를 찾을 수 없습니다.")
        );
        return user.getFollowees().stream().map(Follow::getFollowee).collect(Collectors.toList());
    }
}
