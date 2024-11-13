package com.leets.X.domain.post.repository;

import com.leets.X.domain.post.domain.Repost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepostRepository extends JpaRepository<Repost, Long> {
    boolean existsByUserIdAndPostId(Long userId, Long postId);

}
