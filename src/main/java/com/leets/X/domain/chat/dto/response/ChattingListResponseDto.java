package com.leets.X.domain.chat.dto.response;

import com.leets.X.domain.chat.entity.ChatRoom;
import com.leets.X.domain.user.domain.User;

public record ChattingListResponseDto(
        Long roomId,

        Long senderId,
        String senderName,

        Long opponentId,
        String opponentImageUrl,
        String opponentName,

        LatestMessageDto latestMessageDto
) {
    public static ChattingListResponseDto of(Long roomId, User sender, User opponent, LatestMessageDto latestMessageDto) {
        return new ChattingListResponseDto(
                roomId,
                sender.getId(), sender.getName(),
                opponent.getId(), opponent.getName(), opponent.getImage().getUrl(),
                latestMessageDto
        );
    }
}
