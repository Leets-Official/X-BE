package com.leets.X.domain.chat.redis;

import com.leets.X.domain.chat.dto.PublishMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate redisTemplate;

    public void publish(PublishMessage publishMessage) {
        ChannelTopic topic = new ChannelTopic("/sub/chats/" + publishMessage.getRoomId());
        redisTemplate.convertAndSend(topic.getTopic(), publishMessage);
        log.info("Redis 서버에 메시지 전송 완료");
    }
}
