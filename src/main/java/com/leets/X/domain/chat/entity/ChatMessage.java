package com.leets.X.domain.chat.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.leets.X.domain.chat.dto.PublishMessage;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chatMessage")
@Getter
@Builder
public class ChatMessage {

    @Id
    private String id; // MongoDb에서 사용하는 ObjectId

    private Long roomId;

    private Long senderId;

    private String senderName;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public static ChatMessage of(PublishMessage publishMessage) {
        return ChatMessage.builder()
                .roomId(publishMessage.getRoomId())
                .senderId(publishMessage.getSenderId())
                .senderName(publishMessage.getSenderName())
                .content(publishMessage.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
