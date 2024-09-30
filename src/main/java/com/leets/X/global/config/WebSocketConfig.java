package com.leets.X.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 웹소켓이 handshake를 하기 위해 연결하는 endpoint이다.
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 나중에 도메인으로 변경
                .withSockJS();

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 -> 클라이언트로 발행하는 메시지에 대한 endpoint 설정
        registry.enableSimpleBroker("/sub");

        // 클라이언트 -> 서버로 발행하는 메시지에 대한 endpoint 설정
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
