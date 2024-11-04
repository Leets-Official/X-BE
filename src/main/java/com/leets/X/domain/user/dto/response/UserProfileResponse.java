package com.leets.X.domain.user.dto.response;

import com.leets.X.domain.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserProfileResponse(
        Long userId,
        Boolean isMyProfile,
        String name,
        String customId,
        LocalDateTime createAt,
        Long followCount,
        Long followerCount
) {
    // 정적 팩토리 메서드
    public static UserProfileResponse from(User user, Boolean isMyProfile) {
        return UserProfileResponse.builder()
                .userId(user.getId())
                .isMyProfile(isMyProfile)
                .name(user.getName())
                .customId(user.getCustomId())
                .createAt(user.getCreatedAt())
                .build();
    }
}
