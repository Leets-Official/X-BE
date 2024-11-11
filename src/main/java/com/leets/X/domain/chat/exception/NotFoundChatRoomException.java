package com.leets.X.domain.chat.exception;

import com.leets.X.global.common.exception.BaseException;
import static com.leets.X.domain.chat.exception.ErrorMessage.CHATROOM_NOT_FOUND;

public class NotFoundChatRoomException extends BaseException {
    public NotFoundChatRoomException() {
        super(CHATROOM_NOT_FOUND.getCode(), CHATROOM_NOT_FOUND.getMessage());
    }
}
