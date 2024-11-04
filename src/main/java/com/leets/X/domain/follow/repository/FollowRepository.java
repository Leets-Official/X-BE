package com.leets.X.domain.follow.repository;

import com.leets.X.domain.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // followerId가 포함된 객체 리스트 반환
    List<Follow> findByFollowerId(Long followerId);

    // followedId가 포함된 객체 리스트 반환
    List<Follow> findByFollowedId(Long followedId);

    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

}
