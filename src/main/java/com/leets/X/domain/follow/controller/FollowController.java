package com.leets.X.domain.follow.controller;

import com.leets.X.domain.follow.dto.response.FollowResponse;
import com.leets.X.domain.follow.service.FollowService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.follow.controller.ResponseMessage.*;

@Tag(name = "FOLLOW")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/follows")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{userId}")
    @Operation(summary = "팔로우 하기")
    public ResponseDto<String> follow(@PathVariable Long userId, @AuthenticationPrincipal String email){
        followService.follow(userId, email);
        return ResponseDto.response(FOLLOW_SUCCESS.getCode(), FOLLOW_SUCCESS.getMessage());
    }

    @GetMapping("follower/{userId}")
    @Operation(summary = "해당 유저의 팔로워 조회(해당 유저를 팔로우 하는 사용자 목록")
    public ResponseDto<List<FollowResponse>> getFollowers(@PathVariable Long userId){
        return ResponseDto.response(GET_FOLLOWER_SUCCESS.getCode(), GET_FOLLOWER_SUCCESS.getMessage(), followService.getFollowers(userId));
    }

    @GetMapping("following/{userId}")
    @Operation(summary = "해당 유저의 팔로잉 조회(해당 유저가 팔로우 하는 사용자 목록")
    public ResponseDto<List<FollowResponse>> getFollowings(@PathVariable Long userId){
        return ResponseDto.response(GET_FOLLOWING_SUCCESS.getCode(), GET_FOLLOWING_SUCCESS.getMessage(), followService.getFollowings(userId));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "언팔로우 하기")
    public ResponseDto<String> unfollow(@PathVariable Long userId, @AuthenticationPrincipal String email){
        followService.unfollow(userId, email);
        return ResponseDto.response(UNFOLLOW_SUCCESS.getCode(), UNFOLLOW_SUCCESS.getMessage());
    }

}
