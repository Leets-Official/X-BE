package com.leets.X.domain.follow.dto.response;

import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.post.dto.response.PostUserResponse;
import com.leets.X.domain.user.domain.User;
import lombok.Builder;

@Builder
public record FollowResponse(
        Long id,
        PostUserResponse response
//        boolean followStatus
) {
    public static FollowResponse from(User user) {
        return FollowResponse.builder()
                .id(user.getId())
                .response(toPostUserResponse(user))
                .build();
    }

    public static PostUserResponse toPostUserResponse(User user) {
        ImageDto dto = null;
        Image image = user.getImage();
        if(image != null){
            dto = ImageDto.of(image.getName(), image.getUrl());
        }
        return PostUserResponse.from(user, dto);
    }
}
