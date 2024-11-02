package com.leets.X.domain.chat.dto.request;

public record GetChatRoomRequestDto(

        Long roomId,
        int size,
        int page

){
}
