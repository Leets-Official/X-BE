package com.leets.X.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {


    USER_SAVE_SUCCESS(201,"회원가입에 성공했습니다."),
    LOGIN_SUCCESS(200,"로그인에 성공했습니다.");

    private final int code;
    private final String message;

}

