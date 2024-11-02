package com.leets.X.domain.chat.controller;


import com.leets.X.domain.chat.dto.request.MessageDto;
import com.leets.X.domain.chat.service.PublishMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PublishMessageController {

    private final PublishMessageService publishMessageService;

    // 클라이언트는 "/pub/chats/messages" 로 보낸다.
    @MessageMapping("/chats/messages")
    public void message(MessageDto messageDto) {
        publishMessageService.publishMessage(messageDto);
    }
}
