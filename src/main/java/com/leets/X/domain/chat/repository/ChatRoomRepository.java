package com.leets.X.domain.chat.repository;

import com.leets.X.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

/*
        SELECT room_id FROM chat_room WHERE
           (user1_id = :user1Id AND user2_id = :user2Id)  OR
           (user1_id = :user2Id AND user2_id = :user1Id);
*/

    @Query("SELECT c.id FROM ChatRoom c WHERE " +
            "(c.user1.id = :user1Id AND c.user2.id = :user2Id) OR " +
            "(c.user1.id = :user2Id AND c.user2.id = :user1Id)")
    Optional<Long> findRoomIdByUserIds(@Param("user1Id") Long user1Id, @Param("user2Id") Long user2Id);

}
