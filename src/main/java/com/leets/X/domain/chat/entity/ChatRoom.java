package com.leets.X.domain.chat.entity;


import com.leets.X.domain.user.domain.User;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 접근 레벨 PROTECTED
@Entity
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User user2;

    private String lastMessage;

    public static ChatRoom of(User user1, User user2) {

        return ChatRoom.builder()
                .user1(user1)
                .user2(user2)
                .lastMessage("")
                .build();
    }
}
