package com.leets.X.domain.follow.dto.response;

import com.leets.X.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowResponse(
        Long id,
        String name,
        String customId,
        String introduce
//        boolean followStatus
) {
    public static FollowResponse from(User user) {
        return FollowResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .customId(user.getCustomId())
                .introduce(user.getIntroduce())
                .build();
    }
}
