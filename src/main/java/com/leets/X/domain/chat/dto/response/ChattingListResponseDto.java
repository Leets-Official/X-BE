package com.leets.X.domain.chat.dto.response;

import com.leets.X.domain.chat.entity.ChatRoom;
import com.leets.X.domain.user.domain.User;

public record ChattingListResponseDto(
        Long roomId,

        Long senderId,
        String senderName,

        Long opponentId,
        String opponentName,
        String opponentImageUrl,

        LatestMessageDto latestMessageDto
) {
    public static ChattingListResponseDto of(Long roomId, User sender, User opponent, LatestMessageDto latestMessageDto) {
        String  url = (opponent.getImage()==null) ? ""  : opponent.getImage().getUrl();
        return new ChattingListResponseDto(
                roomId,
                sender.getId(), sender.getName(),
                opponent.getId(), opponent.getName(), url,
                latestMessageDto
        );
    }
}
