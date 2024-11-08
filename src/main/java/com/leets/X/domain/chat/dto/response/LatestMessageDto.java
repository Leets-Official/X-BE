package com.leets.X.domain.chat.dto.response;

import com.leets.X.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;


public record LatestMessageDto(

    String content,
    LocalDateTime createdAt

) {
    public static LatestMessageDto of(ChatMessage message) {
        return new LatestMessageDto(message.getContent(), message.getCreatedAt());
    }
}
