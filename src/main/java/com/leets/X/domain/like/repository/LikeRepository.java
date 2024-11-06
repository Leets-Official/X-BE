package com.leets.X.domain.like.repository;

import com.leets.X.domain.like.domain.Like;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByPostAndUser(Post post, User user); // 좋아요 여부 확인
    void deleteByPostAndUser(Post post, User user); // 좋아요 삭제
    long countByPost(Post post); // 특정 게시물의 좋아요 수 계산
}
