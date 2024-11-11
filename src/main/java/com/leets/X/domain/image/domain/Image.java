package com.leets.X.domain.image.domain;

import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.user.domain.User;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Image extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String name;

    private String url;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id")
    private Post post;

    public static Image from(ImageDto dto, Post post) {
        return Image.builder()
                .name(dto.name())
                .url(dto.url())
                .post(post)
                .build();
    }

    public static Image from(ImageDto dto, User user) {
        return Image.builder()
                .name(dto.name())
                .url(dto.url())
                .user(user)
                .build();
    }

}
