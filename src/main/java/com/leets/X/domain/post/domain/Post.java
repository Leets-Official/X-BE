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

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> commentList = new ArrayList<>();


    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer views;

    private IsDeleted isDeleted;

    private LocalDateTime deletedAt;

    // 좋아요 수를 관리하기 위한 필드
    // 좋아요 수를 관리하기 위한 필드
    @Column(nullable = false) // null을 허용하지 않도록 설정
    private Integer likesCount = 0; // 기본값 설정

    // 서비스에서 좋아요 수를 조회하기 위한 메서드
    public int getLikesCount() {
        return this.likesCount != null ? this.likesCount : 0; // null 체크
    }

    // 좋아요 추가 메서드
    public void addLike() {
        if (this.likesCount == null) {
            this.likesCount = 0; // null인 경우 0으로 초기화
        }
        this.likesCount++;
    }

    // 좋아요 제거 메서드
    public void removeLike() {
        if (this.likesCount != null && this.likesCount > 0) {
            this.likesCount--;
        }
    }

    // 서비스에서 글의 상태를 삭제 상태로 바꾸기 위한 메서드
    public void delete() {
        if (this.isDeleted == IsDeleted.ACTIVE) { // 이미 삭제 상태가 아닐 때만 변경
            this.isDeleted = IsDeleted.DELETED;
        }
}
}
