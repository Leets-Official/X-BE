package com.leets.X.domain.post.repository;

import com.leets.X.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop10ByOrderByCreatedAtDesc(); // 최신 10개 게시물 조회하기위해 구현
}

