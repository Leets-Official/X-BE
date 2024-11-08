package com.leets.X.domain.chat.exception;

import com.leets.X.global.common.exception.BaseException;
import static com.leets.X.domain.chat.exception.ErrorMessage.NOT_FOUND_CHATROOM;

public class NotFoundChatRoomException extends BaseException {
    public NotFoundChatRoomException() {
        super(NOT_FOUND_CHATROOM.getCode(), NOT_FOUND_CHATROOM.getMessage());
    }
}
