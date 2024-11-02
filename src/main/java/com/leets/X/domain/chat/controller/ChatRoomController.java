package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.ChatRoomCheckRequstDto;
import com.leets.X.domain.chat.dto.request.GetChatRoomRequestDto;
import com.leets.X.domain.chat.dto.request.FindChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.dto.response.ChattingListResponseDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.leets.X.domain.chat.controller.ResponseMessage.*;

@RestController
@Slf4j
@Validated
@RequestMapping("/api/v1/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseDto<ChatRoomResponseDto> createChatRoom(@RequestBody FindChatRoomRequestDto findChatRoomRequestDto){
        ChatRoomResponseDto response = chatRoomService.saveChatRoom(findChatRoomRequestDto);
        return ResponseDto.response(CHATROOM_CREATE_SUCCESS.getCode(), CHATROOM_CREATE_SUCCESS.getMessage(), response);
    }

    // 채팅방 하나를 조회해준다. (대화 내역을 돌려준다는 의미)
    @GetMapping
    public ResponseDto<ChattingDto> findChatRoom(
            @RequestBody GetChatRoomRequestDto getChatRoomRequestDto){
        ChattingDto response = chatRoomService.getChatRoom(getChatRoomRequestDto);
        return ResponseDto.response(GET_CHATROOM.getCode(), GET_CHATROOM.getMessage(), response);
    }

    // user1Id와 user2Id의 채팅방이 있는 지 조회
    @GetMapping("/users")
    public ResponseDto<ChatRoomResponseDto> existChatRoom(@RequestBody ChatRoomCheckRequstDto checkDto){
        ChatRoomResponseDto response = chatRoomService.findUser1User2ChatRoom(checkDto);

        return ResponseDto.response(GET_ROOMID.getCode(), GET_ROOMID.getMessage(), response);
    }

    @GetMapping("/chattingList")
    public ResponseDto<List<ChattingListResponseDto>> getChattingList(@RequestParam Long userId){

        List<ChattingListResponseDto> response = chatRoomService.getChattingList(userId);

        return ResponseDto.response(GET_CHATTING_LIST.getCode(), GET_CHATTING_LIST.getMessage(), response);
    }

    @PostMapping("/test") // addListener 테스트 용
    public void addListener(@RequestParam Long roomId) {
        chatRoomService.addListener(roomId);
        log.info("addListener");
    }

    @GetMapping("/test1") // addListener 테스트 용
    public void test1(@RequestParam Long roomId) {
        chatRoomService.hi(roomId);
        log.info("test1");
    }

}
