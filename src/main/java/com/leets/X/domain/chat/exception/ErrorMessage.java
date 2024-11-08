package com.leets.X.domain.chat.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    NOT_FOUND_CHATROOM(400, "해당 채팅방을 찾을 수 없습니다.");

    private final int code;
    private final String message;
}
