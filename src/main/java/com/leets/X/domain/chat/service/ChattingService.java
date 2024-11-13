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
import com.leets.X.domain.user.service.UserService;
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
    private final UserService userService;

    public ChattingDto getChatRoom(Long roomId, Integer page, Integer size, String email) {
        ChatRoom findRoom = validateChatRoom(roomId);
        redisMessageListener.adaptMessageListener(findRoom.getId()); // 채팅방 내역 조회시 리스너 등록 추가 (운영 시 삭제)

        List<ChatMessageResponseDto> chatMessageList = generateChatRoomMessages(roomId, page, size);
        User sender = userService.find(email);

        return findOpponent(sender, findRoom.getUser1(), findRoom.getUser2(), chatMessageList);
    }

    public List<ChattingListResponseDto> getChattingList(String customId, String email) { // 추후 JWT 파싱으로 받아내기.
        User findUser = userService.findByCustomId(customId);
        List<ChatRoom> chatRooms = validateChatRommList(findUser.getId());

        return chatRooms.stream()
                .map(chatRoom -> {
                    ChatMessage latestMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(chatRoom.getId()).orElse(null);
                    LatestMessageDto latestMessageDto = (latestMessage != null)
                            ? LatestMessageDto.of(latestMessage) : new LatestMessageDto("", null);
                    User sender = userService.find(email);
                    return findOpponentToAllChat(chatRoom, sender,chatRoom.getUser1(), chatRoom.getUser2(), latestMessageDto);
                })
                .collect(Collectors.toList());
    }

    /*
    * 리팩토링
    * */

    private List<ChatMessageResponseDto> generateChatRoomMessages(Long roomId, Integer page, Integer size) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(
                        roomId, PageRequest.of(page- 1, size))
                .getContent()
                .stream()
                .map(ChatMessageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    private static ChattingDto findOpponent(User sender, User user1, User user2, List<ChatMessageResponseDto> chatMessageList) {
        if(sender.equals(user1)) { // user1 이 본인
            return ChattingDto.of(user1, user2, chatMessageList);
        }else{
            return ChattingDto.of(user2, user1, chatMessageList);
        }
    }

    private static ChattingListResponseDto findOpponentToAllChat(ChatRoom chatRoom, User sender,User user1, User user2, LatestMessageDto latestMessageDto) {
        if(sender.equals(user1)) { // user1 이 본인
            return ChattingListResponseDto.of(chatRoom.getId(), user1, user2, latestMessageDto);
        }else{
            return ChattingListResponseDto.of(chatRoom.getId(), user2, user1, latestMessageDto);
        }
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
