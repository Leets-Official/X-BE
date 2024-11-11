package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.user.domain.User;


public record PostUserResponse(
        Long userId,
        String name,
        String customId,
        ImageDto profileImage

) {
    public static PostUserResponse from(User user, ImageDto profileImage) {
        return new PostUserResponse(
                user.getId(),
                user.getName(),
                user.getCustomId(),
                profileImage
        );
    }
}
