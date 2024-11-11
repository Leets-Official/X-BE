package com.leets.X.domain.chat.controller;

import com.leets.X.domain.chat.dto.request.FindChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.leets.X.domain.chat.controller.ResponseMessage.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseDto<ChatRoomResponseDto> createChatRoom(@RequestBody @Valid FindChatRoomRequestDto findChatRoomRequestDto){
        ChatRoomResponseDto response = chatRoomService.saveChatRoom(findChatRoomRequestDto);
        return ResponseDto.response(CHATROOM_CREATE_SUCCESS.getCode(), CHATROOM_CREATE_SUCCESS.getMessage(), response);
    }



    // user1Id와 user2Id의 채팅방이 있는 지 조회
    @GetMapping("/{user1Id}/{user2Id}")
    public ResponseDto<ChatRoomResponseDto> existChatRoom(@PathVariable Long user1Id, @PathVariable Long user2Id){
        ChatRoomResponseDto response = chatRoomService.findUser1User2ChatRoom(user1Id , user2Id);

        return ResponseDto.response(ROOMID_GET.getCode(), ROOMID_GET.getMessage(), response);
    }

    @PostMapping("/{roomId}") // addListener 테스트 용
    public void addListener(@PathVariable @NotNull Long roomId) {
        chatRoomService.addListener(roomId);
        log.info(roomId+":addListener");
    }
}
