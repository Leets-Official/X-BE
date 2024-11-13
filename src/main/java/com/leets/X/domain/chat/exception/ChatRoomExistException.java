package com.leets.X.domain.chat.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.chat.exception.ErrorMessage.CHATROOM_EXIST;

public class ChatRoomExistException extends BaseException {
    public ChatRoomExistException() {
        super(CHATROOM_EXIST.getCode(),CHATROOM_EXIST.getMessage());
    }
}
