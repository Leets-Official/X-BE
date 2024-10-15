package com.leets.X.domain.post.domain;

import com.leets.X.domain.comment.domain.Comment;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer views;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;

    private Integer likesCount; // 좋아요 수 추가


    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;
//    private List<Image> imageList;


}
