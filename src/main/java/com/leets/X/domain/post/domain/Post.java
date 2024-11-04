package com.leets.X.domain.post.domain;

import com.leets.X.domain.comment.domain.Comment;
import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.post.domain.enums.IsDeleted;
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
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer views;

    private IsDeleted isDeleted;

    private LocalDateTime deletedAt;

    // 좋아요 수를 관리하기 위한 필드
    // 좋아요 수를 관리하기 위한 필드
    @Column
    private Integer likesCount = 0; // 기본값 설정

    // 서비스에서 좋아요 수를 조회하기 위한 메서드
    public int getLikesCount() {
        if (likesCount == null) {
            this.likesCount = 0;
        }
        return this.likesCount;
    }


    // 서비스에서 글의 상태를 삭제 상태로 바꾸기 위한 메서드
    public void delete() {
        if (this.isDeleted == IsDeleted.ACTIVE) { // 이미 삭제 상태가 아닐 때만 변경
            this.isDeleted = IsDeleted.DELETED;
        }
    }

    // 정적 메서드로 글 생성
    public static Post create(User user, String content) {
        return Post.builder()
                    .user(user)
                    .content(content)
                    .views(0) // 기본 조회 수
                    .isDeleted(IsDeleted.ACTIVE) // 기본값 ACTIVE로 설정
                    .images(new ArrayList<>()) // 빈 리스트로 초기화
                    .commentList(new ArrayList<>()) // 빈 리스트로 초기화
                    .build();
        }
}

