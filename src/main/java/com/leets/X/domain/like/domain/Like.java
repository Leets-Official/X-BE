    package com.leets.X.domain.like.domain;

    import com.leets.X.domain.post.domain.Post;
    import com.leets.X.domain.user.domain.User;
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    // mysql 예약어이기 때문에 별도 지정
    @Table(name = "likes")
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    @Getter
    public class Like {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "like_id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId")
        private Post post;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "userId")
        private User user;

        // 게시물과 사용자 정보를 받는 생성자 추가
        public Like(Post post, User user) {
            this.post = post;
            this.user = user;
        }

    }
