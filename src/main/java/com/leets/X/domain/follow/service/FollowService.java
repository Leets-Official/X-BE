package com.leets.X.domain.follow.service;

import com.leets.X.domain.follow.domain.Follow;
import com.leets.X.domain.follow.dto.response.FollowResponse;
import com.leets.X.domain.follow.exception.AlreadyFollowException;
import com.leets.X.domain.follow.exception.FollowNotFoundException;
import com.leets.X.domain.follow.exception.InvalidFollowException;
import com.leets.X.domain.follow.exception.InvalidUnfollowException;
import com.leets.X.domain.follow.repository.FollowRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    // email -> userId 방향. 팔로우 하는 사람이 follower
    @Transactional
    public void follow(Long userId, String email){
        User follower = userService.find(email);
        User followed = userService.find(userId);

        validate(follower.getId(), followed.getId());
        // 저장
        Follow follow = followRepository.save(Follow.of(follower, followed));
    }

    // user를 팔로우 하는 사람 리스트. user는 팔로우 당하는 사람이므로 followeId에 있어야함
    public List<FollowResponse> getFollowers(Long userId){
        User user = userService.find(userId);
        List<Follow> followerList = followRepository.findByFollowedId(userId);
        List<FollowResponse> followerResponses = new ArrayList<>();

        return followerList.stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollower());
                })
                .toList();
    }

    public List<FollowResponse> getFollowings(Long userId){
        User user = userService.find(userId);
        List<Follow> followerList = followRepository.findByFollowerId(userId);
        List<FollowResponse> followerResponses = new ArrayList<>();

        return followerList.stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollowed());
                })
                .toList();
    }

    // email -> userId 방향의 팔로우 객체 삭제
    @Transactional
    public void unfollow(Long userId, String email){
        User follower = userService.find(email);
        User followed = userService.find(userId);
        // 팔로우 정보가 없는지 체크
        Follow follow = check(follower.getId(), followed.getId());

        followRepository.delete(follow);
    }

    public Follow find(Long followerId, Long followedId){
        return followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(FollowNotFoundException::new);
    }

    private void validate(Long followerId, Long followedId){
        if(followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)){
            throw new AlreadyFollowException();
        }
        if(followerId.equals(followedId)){
            throw new InvalidFollowException();
        }
    }

    // 팔로우 되어 있는지 확인
    private Follow check(Long followerId, Long followedId){
        if(!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)){
            throw new InvalidUnfollowException();
        }
        return find(followerId, followedId);
    }

}
