package com.leets.X.domain.chat.controller;

import com.leets.X.domain.chat.dto.request.GetChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.dto.response.ChattingListResponseDto;
import com.leets.X.domain.chat.service.ChattingService;
import com.leets.X.global.common.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.chat.controller.ResponseMessage.GET_CHATROOM;
import static com.leets.X.domain.chat.controller.ResponseMessage.GET_CHATTING_LIST;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;

    // 채팅방 하나를 조회해준다. (대화 내역을 돌려준다는 의미)
    @GetMapping("/chatting")
    public ResponseDto<ChattingDto> findChatting( @RequestBody @Valid GetChatRoomRequestDto getChatRoomRequestDto){
        ChattingDto response = chattingService.getChatRoom(getChatRoomRequestDto);
        return ResponseDto.response(GET_CHATROOM.getCode(), GET_CHATROOM.getMessage(), response);
    }


    @GetMapping("/chattingList")
    public ResponseDto<List<ChattingListResponseDto>> findChattingList(@RequestParam Long userId){
        List<ChattingListResponseDto> response = chattingService.getChattingList(userId);
        return ResponseDto.response(GET_CHATTING_LIST.getCode(), GET_CHATTING_LIST.getMessage(), response);
    }

}
