package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.image.dto.response.ImageResponse;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.post.domain.enums.Type;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostResponseDto(
        Long id,
        String content,
        Integer views,
        IsDeleted isDeleted,
        LocalDateTime createdAt,
        PostUserResponse user,
        Long likeCount,
        Boolean isLikedByUser, // 좋아요 여부 확인
        Type postType,
        Boolean myPost,
        String replyTo,
        List<ImageResponse> images,
        List<PostResponseDto> replies
) {

}


