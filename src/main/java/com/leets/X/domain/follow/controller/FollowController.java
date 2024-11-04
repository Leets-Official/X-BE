package com.leets.X.domain.follow.controller;

import com.leets.X.domain.follow.dto.response.FollowResponse;
import com.leets.X.domain.follow.service.FollowService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.follow.controller.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{userId}")
    public ResponseDto<String> follow(@PathVariable Long userId, @AuthenticationPrincipal String email){
        followService.follow(userId, email);
        return ResponseDto.response(FOLLOW_SUCCESS.getCode(), FOLLOW_SUCCESS.getMessage());
    }

    @GetMapping("follower/{userId}")
    public ResponseDto<List<FollowResponse>> getFollowers(@PathVariable Long userId){
        return ResponseDto.response(GET_FOLLOWER_SUCCESS.getCode(), GET_FOLLOWER_SUCCESS.getMessage(), followService.getFollowers(userId));
    }

    @GetMapping("following/{userId}")
    public ResponseDto<List<FollowResponse>> getFollowings(@PathVariable Long userId){
        return ResponseDto.response(GET_FOLLOWING_SUCCESS.getCode(), GET_FOLLOWING_SUCCESS.getMessage(), followService.getFollowings(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseDto<String> unfollow(@PathVariable Long userId, @AuthenticationPrincipal String email){
        followService.unfollow(userId, email);
        return ResponseDto.response(UNFOLLOW_SUCCESS.getCode(), UNFOLLOW_SUCCESS.getMessage());
    }

}
