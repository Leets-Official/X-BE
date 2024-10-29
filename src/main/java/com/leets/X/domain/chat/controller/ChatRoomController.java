package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.ChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@Validated
@RequestMapping("/api/v1/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/check")
    public void getChatRoomByUser1IdUser2Id(){


    }

    @PostMapping
    public ResponseDto<ChatRoomResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto){
        ChatRoomResponseDto response = chatRoomService.save(chatRoomRequestDto);
        return ResponseDto.response(200, "Successfully create ChatRoom!", response);
    }

//    @GetMapping("/{roomId}")
//    public ResponseDto<ChatRoomResponseDto> findChatRoom(@PathVariable("roomNo") Integer roomNo){
//        ChatRoomResponseDto response = new ChatRoomResponseDto(1L);
//        return ResponseDto.response(200, "Successfully return ChatRoom", response);
//    }

}
