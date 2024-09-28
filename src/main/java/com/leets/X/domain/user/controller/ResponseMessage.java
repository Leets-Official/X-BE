package com.leets.X.domain.user.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {


    SUCCESS_SAVE(201,"회원가입에 성공했습니다.");

    private final int code;
    private final String message;

}

