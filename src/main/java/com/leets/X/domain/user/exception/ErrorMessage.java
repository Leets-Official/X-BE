package com.leets.X.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    USER_NOT_FOUND(404,"존재하지 않는 유저입니다."),
    USER1_NOT_FOUND(404, "유저1은 존재하지 않습니다."),
    USER2_NOT_FOUND(404, "유저2는 존재하지 않습니다."),
    DUPLICATE_CUSTOMID(400, "중복된 CustomId입니다.");

    // 송우석 추가 내용

    private final int code;
    private final String message;

}
