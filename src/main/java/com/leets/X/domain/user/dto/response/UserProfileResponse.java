package com.leets.X.domain.user.dto.response;

import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponse(
        Long userId,
        Boolean isMyProfile,
        String name,
        String customId,
        String introduce,
        Long followerCount,
        Long followingCount,
        Boolean isFollowing,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        ImageDto profileImage
        ) {
    // 정적 팩토리 메서드
    public static UserProfileResponse from(User user, Boolean isMyProfile, Boolean isFollowing, ImageDto image) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .isMyProfile(isMyProfile)
                .name(user.getName())
                .customId(user.getCustomId())
                .introduce(user.getIntroduce())
                .followerCount(user.getFollowerCount())
                .followingCount(user.getFollowingCount())
                .isFollowing(isFollowing)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .profileImage(image)
                .build();
    }
}
