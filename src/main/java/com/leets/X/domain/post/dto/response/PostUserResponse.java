package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.user.domain.User;


public record PostUserResponse(
        Long userId,
        String name,
        String customId
) {
    public static PostUserResponse from(User user) {
        return new PostUserResponse(
                user.getId(),
                user.getName(),
                user.getCustomId()
        );
    }
}
