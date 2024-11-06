package com.leets.X.domain.follow.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    FOLLOW_SUCCESS(200, "팔로우에 성공했습니다."),
    GET_FOLLOWER_SUCCESS(200, "팔로워 목록 조회에 성공했습니다."),
    GET_FOLLOWING_SUCCESS(200, "팔로잉 목록 조회에 성공했습니다."),
    UNFOLLOW_SUCCESS(200, "언팔로우에 성공했습니다.");

    private final int code;
    private final String message;
}
