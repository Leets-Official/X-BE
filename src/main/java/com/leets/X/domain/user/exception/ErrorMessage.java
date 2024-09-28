package com.leets.X.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    USER_NOT_FOUND(404,"존재하지 않는 유저입니다.");

    private final int code;
    private final String message;

}
