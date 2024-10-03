package com.leets.X.domain.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.leets.X.domain.chat.dto.PublishMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PublishMessageResponseDto {

    private Long roomId;

    private Long senderId;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public PublishMessageResponseDto(PublishMessage publishMessage) {
        this.roomId = publishMessage.getRoomId();
        this.senderId = publishMessage.getSenderId();
        this.content = publishMessage.getContent();
        this.createAt = LocalDateTime.now();
    }
}
