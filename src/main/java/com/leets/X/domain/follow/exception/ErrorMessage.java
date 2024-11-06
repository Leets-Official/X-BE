package com.leets.X.domain.follow.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ALREADY_FOLLOW(400,"이미 팔로우한 상태입니다."),
    INVALID_FOLLOW(400, "자기 자신은 팔로우 할 수 없습니다."),
    FOLLOW_NOT_FOUND(404, "팔로우 데이터를 찾을 수 없습니다."),
    INVALID_UNFOLLOW(400, "팔로우 하지 않은 상대는 언팔로우 할 수 없습니다.");


    private final int code;
    private final String message;

}
