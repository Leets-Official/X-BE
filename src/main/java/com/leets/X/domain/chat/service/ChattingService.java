package com.leets.X.domain.chat.service;

import com.leets.X.domain.chat.dto.request.GetChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.ChatMessageResponseDto;
import com.leets.X.domain.chat.dto.response.ChattingDto;
import com.leets.X.domain.chat.dto.response.ChattingListResponseDto;
import com.leets.X.domain.chat.dto.response.LatestMessageDto;
import com.leets.X.domain.chat.entity.ChatMessage;
import com.leets.X.domain.chat.entity.ChatRoom;
import com.leets.X.domain.chat.exception.NotFoundChatRoomException;
import com.leets.X.domain.chat.redis.RedisListener;
import com.leets.X.domain.chat.repository.ChatMessageRepository;
import com.leets.X.domain.chat.repository.ChatRoomRepository;
import com.leets.X.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final RedisListener redisMessageListener;

    public ChattingDto getChatRoom(Long roomId, Integer page, Integer size) {
        ChatRoom findRoom = validateChatRoom(roomId);
        User user1 = findRoom.getUser1();
        User user2 = findRoom.getUser2();
        redisMessageListener.adaptMessageListener(findRoom.getId()); // 채팅방 내역 조회시 리스너 등록 추가 (운영 시 삭제)

        List<ChatMessageResponseDto> chatMessageList = generateChatRoomMessages(roomId, page, size);
        return new ChattingDto(user1.getId(), user2.getId(), user1.getCustomId(), user2.getCustomId(), chatMessageList);
    }


    public List<ChattingListResponseDto> getChattingList(Long userId) { // 추후 JWT 파싱으로 받아내기.
        List<ChatRoom> chatRooms = validateChatRommList(userId);

        return chatRooms.stream()
                .map(chatRoom -> {
                    ChatMessage latestMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom.getId()).orElse(null);
                    LatestMessageDto latestMessageDto = (latestMessage != null)
                            ? LatestMessageDto.of(latestMessage) : new LatestMessageDto("", null);
                    return ChattingListResponseDto.of(chatRoom, latestMessageDto);
                })
                .collect(Collectors.toList());
    }

    private List<ChatMessageResponseDto> generateChatRoomMessages(Long roomId, Integer page, Integer size) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(
                        roomId, PageRequest.of(page- 1, size))
                .getContent()
                .stream()
                .map(ChatMessageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }


    private ChatRoom validateChatRoom(Long roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);
    }

    private List<ChatRoom> validateChatRommList(Long userId) {
        return chatRoomRepository.findRoomsByUserId(userId)
                .orElseThrow(NotFoundChatRoomException::new);
    }

}
