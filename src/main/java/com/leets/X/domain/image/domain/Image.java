package com.leets.X.domain.image.domain;

import com.leets.X.domain.image.dto.request.ImageSaveRequest;
import com.leets.X.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String name;

    private String url;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "post_Id")
    private Post post;

    public static Image from(ImageSaveRequest dto) {
        return Image.builder()
                .name(dto.name())
                .url(dto.url())
                .build();
    }

}
