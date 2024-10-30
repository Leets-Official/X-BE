package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.ChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/check")
    public void getChatRoomByUser1IdUser2Id(){


    }

    @PostMapping("/chatRoom")
    public ResponseDto<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto){
        ChatRoomResponseDto response = chatRoomService.save(chatRoomRequestDto);
        return ResponseDto.response(200, "Successfully create ChatRoom!", response);
    }

    // 채팅방 하나를 조회해준다. (대화 내용을 돌려준다는 의미)
    @GetMapping("/chatRoom")
    public ResponseDto<ChatRoomResponseDto> findChatRoom(
                                    @RequestParam Long roomId, @RequestParam int size, @RequestParam int page ){
        chatRoomService.findByChatRoom(roomId, size, page);
        ChatRoomResponseDto response = new ChatRoomResponseDto(1L);
        return ResponseDto.response(200, "Successfully return ChatRoom", response);
    }

}
