package com.leets.X.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record FindChatRoomRequestDto(

        @NotNull Long user1Id,
        @NotNull Long user2Id
){}
