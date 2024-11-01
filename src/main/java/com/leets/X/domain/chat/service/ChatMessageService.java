package com.leets.X.domain.chat.service;

import com.leets.X.domain.chat.dto.PublishMessage;
import com.leets.X.domain.chat.dto.request.MessageDto;
import com.leets.X.domain.chat.entity.ChatMessage;
import com.leets.X.domain.chat.redis.RedisPublisher;
import com.leets.X.domain.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final RedisPublisher redisPublisher;
    private final ChatMessageRepository chatMessageRepository;

    public void publishMessage(MessageDto messageDto) {
        PublishMessage publishMessage =
                new PublishMessage(messageDto.getRoomId(), messageDto.getSenderId(), messageDto.getSenderName(), messageDto.getContent());

        redisPublisher.publish(publishMessage);
        chatMessageRepository.save(ChatMessage.of(publishMessage));
    }

}
