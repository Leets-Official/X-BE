package com.leets.X.domain.post.domain;

import com.leets.X.domain.comment.domain.Comment;
import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.user.domain.User;
import com.leets.X.global.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();


    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer views;

    private Boolean isDeleted;

    private LocalDateTime deletedAt;

    //service에서 좋아요 수를 조회하기 위한 메서드
    public int getLikesCount() {
        return this.likes.size();
    }


}
