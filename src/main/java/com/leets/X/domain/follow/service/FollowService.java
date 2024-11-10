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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional
    public void follow(Long userId, String email) {
        User follower = userService.find(email);
        User followed = userService.find(userId);

        validate(follower.getId(), followed.getId());

        Follow follow = followRepository.save(Follow.of(follower, followed));

        follower.addFollowing(follow);
        follower.updateFollowingCount(countFollowing(follower));

        followed.addFollower(follow);
        followed.updateFollowerCount(countFollower(followed));
    }

    public List<FollowResponse> getFollowers(Long userId) {
        User user = userService.find(userId);

        List<Follow> followerList = user.getFollowerList();

        return followerList.stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollower());
                })
                .toList();
    }

    public List<FollowResponse> getFollowings(Long userId) {
        User user = userService.find(userId);

        List<Follow> followingList = user.getFollowingList();

        return followingList.stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollowed());
                })
                .toList();
    }

    @Transactional
    public void unfollow(Long userId, String email) {
        User follower = userService.find(email);
        User followed = userService.find(userId);

        Follow follow = check(follower.getId(), followed.getId());

        follower.removeFollowing(follow);
        follower.decreaseFollowingCount();

        followed.removeFollower(follow);
        followed.decreaseFollowerCount();

        followRepository.delete(follow);
    }

    public Follow find(Long followerId, Long followedId) {
        return followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(FollowNotFoundException::new);
    }

    private Long countFollower(User user) {
        return user.getFollowerList().stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollower());
                })
                .count();
    }

    private Long countFollowing(User user) {
        return user.getFollowingList().stream()
                .map(follow -> {
                    return FollowResponse.from(follow.getFollowed());
                })
                .count();
    }

    // 기존 팔로우 정보가 있는지, 나한테 요청을 하지 않는지 검증
    private void validate(Long followerId, Long followedId) {
        if (followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new AlreadyFollowException();
        }
        if (followerId.equals(followedId)) {
            throw new InvalidFollowException();
        }
    }

    // 팔로우 되어 있는지 확인
    private Follow check(Long followerId, Long followedId) {
        if (!followRepository.existsByFollowerIdAndFollowedId(followerId, followedId)) {
            throw new InvalidUnfollowException();
        }
        return find(followerId, followedId);
    }

}
