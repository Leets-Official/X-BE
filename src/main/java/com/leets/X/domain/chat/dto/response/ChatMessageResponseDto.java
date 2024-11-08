package com.leets.X.domain.chat.dto.response;

import com.leets.X.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponseDto(

        Long senderId,
        String senderName,
        String content,
        LocalDateTime createdAt

) {
    public static ChatMessageResponseDto fromEntity(ChatMessage chatMessage){
        return new ChatMessageResponseDto(
                chatMessage.getSenderId(),
                chatMessage.getSenderName(),
                chatMessage.getContent(),
                chatMessage.getCreatedAt()
        );
    }
}
