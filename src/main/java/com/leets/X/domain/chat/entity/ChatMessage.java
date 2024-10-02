package com.leets.X.domain.chat.entity;


import com.leets.X.domain.chat.dto.PublishMessage;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Builder
public class ChatMessage {

    @Id
    private Long id; // MongoDb에서 사용하는 ObjectId

    private Long roomId;

    private Long senderId;

    private String content;

    // 메시지는 수정할 수 X -> BaseEntity 상속 X
    private LocalDateTime createdAt;

    public static ChatMessage of(PublishMessage publishMessage) {
        return ChatMessage.builder()
                .roomId(publishMessage.getRoomId())
                .senderId(publishMessage.getSenderId())
                .content(publishMessage.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
