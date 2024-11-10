package com.leets.X.domain.post.repository;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop10ByOrderByCreatedAtDesc(); // 최신 10개 게시물 조회하기위해 구현


// 부모 글만 조회하고, 자식 글은 포함하지 않음
    List<Post> findByParentIsNullAndIsDeletedOrderByCreatedAtDesc(IsDeleted isDeleted);

    // 특정 부모 글과 자식 글을 함께 조회하는 메서드 (페치 조인 자동 생성)
    Optional<Post> findWithRepliesByIdAndIsDeleted(Long postId, IsDeleted isDeleted);

}



