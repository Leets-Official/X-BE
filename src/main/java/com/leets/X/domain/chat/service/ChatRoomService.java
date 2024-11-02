package com.leets.X.domain.chat.service;

import com.leets.X.domain.chat.dto.request.ChatRoomCheckRequstDto;
import com.leets.X.domain.chat.dto.request.FindChatRoomRequestDto;
import com.leets.X.domain.chat.dto.response.*;
import com.leets.X.domain.chat.entity.ChatRoom;
import com.leets.X.domain.chat.redis.RedisListener;
import com.leets.X.domain.chat.repository.ChatRoomRepository;
import com.leets.X.domain.user.domain.User;
import com.leets.X.domain.user.exception.UserNotFoundException;
import com.leets.X.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    public ChatRoomResponseDto saveChatRoom(FindChatRoomRequestDto findChatRoomRequestDto) {

        // 이 부분은 UserService에 비지니스 로직을 추가 해달라고 한 뒤 or 추가하고 UserService 단에서 접근하는게 나을거 같다.
        Optional<User> user1 = userRepository.findById(findChatRoomRequestDto.user1Id());
        Optional<User> user2 = userRepository.findById(findChatRoomRequestDto.user2Id());

        if (user1.isPresent() && user2.isPresent()) {
            ChatRoom savedRoom = chatRoomRepository.save(ChatRoom.of(user1.get(), user2.get())); // 채팅방 RDB에 저장
            redisMessageListener.adaptMessageListener(savedRoom.getId()); // 리스너 등록

            return new ChatRoomResponseDto(savedRoom.getId());
        } else {
            // 사용자 없음 예외 발생
            //int code = (user2.isPresent()) ? USER1_NOT_FOUND.getCode() : USER2_NOT_FOUND.getCode();
            //String message = (user2.isPresent()) ? USER1_NOT_FOUND.getMessage()  : USER2_NOT_FOUND.getMessage();
            // UserNotFoundException 예외 발생
            throw new UserNotFoundException();
        }
    }



    public ChatRoomResponseDto findUser1User2ChatRoom(ChatRoomCheckRequstDto checkDto) {
        Long result = chatRoomRepository.findRoomIdByUserIds(checkDto.user1Id(), checkDto.user2Id()).orElse(-1L);
        return new ChatRoomResponseDto(result);
    }

    // 테스트를 위해서 만들어둠. 추후 삭제
    public void addListener(Long roomId) {
        redisMessageListener.adaptMessageListener(roomId); // 리스너 등록
    }


//    public void hi(Long roomId){
//        ChatMessage chatMessage = chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(roomId).get();
//        System.out.println(chatMessage.getRoomId() + " " + chatMessage.getCreatedAt() + " " + chatMessage.getContent());
//    }

}
