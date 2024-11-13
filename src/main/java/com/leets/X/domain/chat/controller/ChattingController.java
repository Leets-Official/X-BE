package com.leets.X.domain.chat.controller;

import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.dto.response.ChattingListResponseDto;
import com.leets.X.domain.chat.service.ChattingService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.chat.controller.ResponseMessage.CHATROOM_GET;
import static com.leets.X.domain.chat.controller.ResponseMessage.CHATTINGLIST_GET;

@Tag(name="Chatting(ChatMessage)")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;

    @GetMapping("/chatting/{roomId}/{page}/{size}")
    @Operation(summary = "하나의 채팅방 + 해당 채팅 내역 조회")
    public ResponseDto<ChattingDto> findChatting(@PathVariable Long roomId, @PathVariable Integer page,
                                                 @PathVariable Integer size, @AuthenticationPrincipal String email) {
        ChattingDto response = chattingService.getChatRoom(roomId, page, size, email);
        return ResponseDto.response(CHATROOM_GET.getCode(), CHATROOM_GET.getMessage(), response);
    }


    @GetMapping("/chattingList/{userId}")
    @Operation(summary = "유저가 속한 모든 채팅방 조회")
    public ResponseDto<List<ChattingListResponseDto>> findChattingList(@PathVariable Long userId, @AuthenticationPrincipal String email){
        List<ChattingListResponseDto> response = chattingService.getChattingList(userId, email);
        return ResponseDto.response(CHATTINGLIST_GET.getCode(), CHATTINGLIST_GET.getMessage(), response);
    }

}
