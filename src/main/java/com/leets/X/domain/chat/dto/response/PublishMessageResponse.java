package com.leets.X.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leets.X.domain.chat.dto.PublishMessage;

import java.time.LocalDateTime;

public record PublishMessageResponse(
        Long roomId,
        Long senderId,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createAt
) {
    public PublishMessageResponse(PublishMessage publishMessage) {
        this(publishMessage.getRoomId(), publishMessage.getSenderId(), publishMessage.getContent(), LocalDateTime.now());
    }
}
