package com.leets.X.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record FindChatRoomRequestDto(

        @NotNull String custom1Id,
        @NotNull String custom2Id
){}
