package com.leets.X.domain.chat.controller;

import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.dto.response.ChattingListResponseDto;
import com.leets.X.domain.chat.service.ChattingService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.chat.controller.ResponseMessage.CHATROOM_GET;
import static com.leets.X.domain.chat.controller.ResponseMessage.CHATTINGLIST_GET;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;

    // 채팅방 하나를 조회해준다. (대화 내역을 돌려준다는 의미)
    @GetMapping("/chatting/{roomId}/{page}/{size}")
    public ResponseDto<ChattingDto> findChatting(@PathVariable Long roomId, @PathVariable Integer page, @PathVariable Integer size) {
        ChattingDto response = chattingService.getChatRoom(roomId, page, size);
        return ResponseDto.response(CHATROOM_GET.getCode(), CHATROOM_GET.getMessage(), response);
    }


    @GetMapping("/chattingList/{userId}")
    public ResponseDto<List<ChattingListResponseDto>> findChattingList(@PathVariable Long userId){
        List<ChattingListResponseDto> response = chattingService.getChattingList(userId);
        return ResponseDto.response(CHATTINGLIST_GET.getCode(), CHATTINGLIST_GET.getMessage(), response);
    }

}
