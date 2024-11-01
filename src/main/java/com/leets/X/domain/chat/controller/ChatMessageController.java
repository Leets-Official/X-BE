package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.MessageDto;
import com.leets.X.domain.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    // 클라이언트는 /pub/chats/messages/ + roomId로 보낸다.
    @MessageMapping("/chats/messages/{room-id}")
    public void message(MessageDto messageDto) {
        chatMessageService.publishMessage(messageDto);
    }
}
