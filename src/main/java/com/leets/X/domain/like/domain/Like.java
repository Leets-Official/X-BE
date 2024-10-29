    package com.leets.X.domain.like.domain;

    import com.leets.X.domain.post.domain.Post;
    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.NoArgsConstructor;

    @Entity
    // mysql 예약어이기 때문에 별도 지정
    @Table(name = "likes")
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public class Like {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "like_id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "postId")
        private Post post;
    }
