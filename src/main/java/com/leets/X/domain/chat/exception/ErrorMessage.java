package com.leets.X.domain.chat.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    CHATROOM_NOT_FOUND(400, "해당 채팅방을 찾을 수 없습니다."),
    CHATROOM_EXIST(400, "해당 채팅방이 이미 존재합니다");

    private final int code;
    private final String message;
}
