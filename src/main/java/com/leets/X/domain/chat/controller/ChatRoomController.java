package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.ChatRoomCheckRequstDto;
import com.leets.X.domain.chat.dto.request.ChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.leets.X.domain.chat.controller.ResponseMessage.*;

@RestController
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
        ChatRoomResponseDto response = chatRoomService.saveChatRoom(chatRoomRequestDto);
        return ResponseDto.response(CHATROOM_CREATE_SUCCESS.getCode(), CHATROOM_CREATE_SUCCESS.getMessage(), response);
    }

    // 채팅방 하나를 조회해준다. (대화 내역을 돌려준다는 의미)
    @GetMapping
    public ResponseDto<ChattingDto> findChatRoom(
                                    @RequestParam Long roomId, @RequestParam int size, @RequestParam int page ){
        ChattingDto response = chatRoomService.getChatRoom(roomId, size, page);
        return ResponseDto.response(GET_CHATROOM.getCode(), GET_CHATROOM.getMessage(), response);
    }

    // user1Id와 user2Id의 채팅방이 있는 지 조회
    @GetMapping("/user")
    public ResponseDto<ChatRoomResponseDto> existChatRoom(@RequestBody ChatRoomCheckRequstDto checkDto){
        ChatRoomResponseDto response = chatRoomService.findUser1User2ChatRoom(checkDto);

        return ResponseDto.response(GET_ROOMID.getCode(), GET_ROOMID.getMessage(), response);

    }

    @PostMapping("/test") // addListener 테스트 용
    public void addListener(@RequestParam Long roomId) {
        chatRoomService.addListener(roomId);
        log.info("addListener");
    }

}
