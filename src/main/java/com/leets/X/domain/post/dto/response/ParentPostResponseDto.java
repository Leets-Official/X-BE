package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.image.dto.response.ImageResponse;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.domain.enums.Type;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ParentPostResponseDto(
        Long id,
        String content,
        Integer views,
        IsDeleted isDeleted,
        LocalDateTime createdAt,
        PostUserResponse user,
        Long likeCount,
        Long repostingUserId,
        Type postType,
        Boolean myPost,
        Boolean isLikedByUser,
        String replyTo,
        List<ImageResponse> images
) {

}
