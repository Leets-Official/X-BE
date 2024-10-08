package com.leets.X.domain.chat.entity;


import com.leets.X.domain.chat.dto.PublishMessage;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Getter
@Builder
public class ChatMessage extends BaseTimeEntity {

    @Id
    private Long id; // MongoDb에서 사용하는 ObjectId

    private Long roomId;

    private Long senderId;

    private String content;

    public static ChatMessage of(PublishMessage publishMessage) {
        return ChatMessage.builder()
                .roomId(publishMessage.getRoomId())
                .senderId(publishMessage.getSenderId())
                .content(publishMessage.getContent())
                .build();
    }

}
