package com.leets.X.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record GetChatRoomRequestDto(

        @NotNull Long roomId,
        @NotNull Integer size,
        @NotNull Integer page

){
}
