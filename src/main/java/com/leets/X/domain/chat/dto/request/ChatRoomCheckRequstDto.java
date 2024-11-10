package com.leets.X.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChatRoomCheckRequstDto(

        @NotNull Long user1Id,
        @NotNull Long user2Id

) {
}
