package com.leets.X.domain.follow.domain;

import com.leets.X.domain.user.domain.User;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followed;

    public static Follow of(User follower, User followed) {
        return Follow.builder()
                .follower(follower)
                .followed(followed)
                .build();
    }

}
