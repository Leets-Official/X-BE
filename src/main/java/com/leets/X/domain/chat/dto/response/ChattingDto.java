package com.leets.X.domain.chat.dto.response;

import com.leets.X.domain.user.domain.User;

import java.util.List;

public record ChattingDto(
    Long senderId,
    String senderName,

    Long opponentId,
    String opponentName,
    String opponentImageUrl,

    List<ChatMessageResponseDto> chatMessageList
) {

    public static ChattingDto of(User sender, User opponent, List<ChatMessageResponseDto> chatMessageList) {
        String  url = (opponent.getImage()==null) ? ""  : opponent.getImage().getUrl();
        return new ChattingDto(
                sender.getId(), sender.getName(),
                opponent.getId(), opponent.getName(), url,
                chatMessageList
        );
    }

}
