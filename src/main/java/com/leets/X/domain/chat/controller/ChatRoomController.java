package com.leets.X.domain.chat.controller;

import com.leets.X.domain.chat.dto.request.FindChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatRoomResponseDto;
import com.leets.X.domain.chat.service.ChatRoomService;
import com.leets.X.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.leets.X.domain.chat.controller.ResponseMessage.*;

@Tag(name="ChatRoom")
@Slf4j
@RestController
@RequestMapping("/api/v1/chatRoom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    @Operation(summary = "채팅 방 생성")
    public ResponseDto<ChatRoomResponseDto> createChatRoom(@RequestBody @Valid FindChatRoomRequestDto findChatRoomRequestDto){
        ChatRoomResponseDto response = chatRoomService.saveChatRoom(findChatRoomRequestDto);
        return ResponseDto.response(CHATROOM_CREATE_SUCCESS.getCode(), CHATROOM_CREATE_SUCCESS.getMessage(), response);
    }



    // user1Id와 user2Id의 채팅방이 있는 지 조회
    @GetMapping("/{custom1Id}/{custom2Id}")
    @Operation(summary = "채팅방 존재 여부 확인")
    public ResponseDto<ChatRoomResponseDto> existChatRoom(@PathVariable String custom1Id, @PathVariable String custom2Id){
        ChatRoomResponseDto response = chatRoomService.findUser1User2ChatRoom(custom1Id , custom2Id);

        return ResponseDto.response(ROOMID_GET.getCode(), ROOMID_GET.getMessage(), response);
    }

    @PostMapping("/{roomId}") // addListener 테스트 용
    @Operation(summary = "채팅 방 addListener 테스트")
    public void addListener(@PathVariable @NotNull Long roomId) {
        chatRoomService.addListener(roomId);
        log.info(roomId+":addListener");
    }
}
