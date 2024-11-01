package com.leets.X.domain.chat.dto.response;

import java.util.List;

public record ChattingDto(
    Long user1Id,
    Long user2Id,
    String user1Name,
    String user2Name,
    List<ChatMessageResponseDto> chatMessageList
) {
}
