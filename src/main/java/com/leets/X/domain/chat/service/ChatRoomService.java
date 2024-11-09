package com.leets.X.domain.chat.service;

import com.leets.X.domain.chat.dto.request.ChatRoomCheckRequstDto;
import com.leets.X.domain.chat.dto.request.FindChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.*;
import com.leets.X.domain.chat.entity.ChatRoom;
import com.leets.X.domain.chat.exception.ChatRoomExistException;
import com.leets.X.domain.chat.exception.NotFoundChatRoomException;
import com.leets.X.domain.chat.redis.RedisListener;
import com.leets.X.domain.chat.repository.ChatRoomRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.domain.user.repository.UserRepository;
import com.leets.X.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final RedisListener redisMessageListener;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    public ChatRoomResponseDto saveChatRoom(FindChatRoomRequestDto findChatRoomRequestDto) {
        User user1 = userService.find(findChatRoomRequestDto.user1Id());
        User user2 = userService.find(findChatRoomRequestDto.user2Id());

        checkChatRoom(user1.getId(), user2.getId());
        ChatRoom savedRoom = chatRoomRepository.save(ChatRoom.of(user1, user2)); // 채팅방 RDB에 저장

        redisMessageListener.adaptMessageListener(savedRoom.getId()); // 리스너 등록
        return new ChatRoomResponseDto(savedRoom.getId());
    }

    public ChatRoomResponseDto findUser1User2ChatRoom(Long user1Id , Long user2Id) {
        return new ChatRoomResponseDto(findUsersChatRoom(user1Id, user2Id));
    }

    // 테스트를 위해서 만들어둠. 추후 삭제
    public void addListener(Long roomId) {
        redisMessageListener.adaptMessageListener(roomId); // 리스너 등록
    }



    private void checkChatRoom(Long user1Id, Long user2Id) {
        chatRoomRepository.findRoomIdByUserIds(user1Id, user2Id).ifPresent(c->{
            throw new ChatRoomExistException();
        });
    }

    private Long findUsersChatRoom(Long user1Id, Long user2Id) {
        return chatRoomRepository.findRoomIdByUserIds(user1Id, user2Id)
                .orElseThrow(NotFoundChatRoomException::new);
    }
}
