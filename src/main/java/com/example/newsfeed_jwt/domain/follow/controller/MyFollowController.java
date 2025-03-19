package com.example.newsfeed_jwt.domain.follow.controller;

import com.example.newsfeed_jwt.domain.follow.dto.FollowDto;
import com.example.newsfeed_jwt.domain.follow.entity.Follow;
import com.example.newsfeed_jwt.domain.follow.repository.FollowRepository;
import com.example.newsfeed_jwt.domain.user.entity.User;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyFollowController {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    //팔로워 목록 조회
    @GetMapping("/api/v1/my/follower")
    public List<FollowDto> follower() {
        Long myId = 1L;
        User user = userRepository.findById(myId).orElseThrow(
                () -> new IllegalStateException("해당 유저는 존재하지 않습니다.")
        );

        List<FollowDto> followDtoList = new ArrayList<>();
        for (Follow follow : user.getFollowers()) {
            FollowDto followDto = getFollowDto(follow.getFollower());
            followDtoList.add(followDto);
        }
        return followDtoList;
    }

    //팔로잉 목록 조회
    @GetMapping("/api/v1/my/followee")
    public List<FollowDto> followee() {
        Long myId = 1L;
        User user = userRepository.findById(myId).orElseThrow(
                () -> new IllegalStateException("해당 유저는 존재하지 않습니다.")
        );
        List<FollowDto> followDtoList = new ArrayList<>();
        for (Follow follow : user.getFollowees()) {
            FollowDto followDto = getFollowDto(follow.getFollowee());
            followDtoList.add(followDto);
        }
        return followDtoList;
    }

    private FollowDto getFollowDto(User user) {
        return new FollowDto(user.getId(), user.getEmail());
    }
}
