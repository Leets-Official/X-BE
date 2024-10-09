package com.leets.X.global.auth.jwt.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    REISSUE_TOKEN(200, "토큰 재발급에 성공 했습니다.");

    private final int code;
    private final String message;
}
