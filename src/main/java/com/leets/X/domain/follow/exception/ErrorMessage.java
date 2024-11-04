package com.leets.X.domain.follow.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    ALREADY_FOLLOW(400,"이미 팔로우한 상태입니다."),
    INVALID_FOLLOW(400, "자기 자신은 팔로우 할 수 없습니다.");

    private final int code;
    private final String message;

}
