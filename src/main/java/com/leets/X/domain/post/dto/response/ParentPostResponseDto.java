package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.user.domain.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record ParentPostResponseDto(
        Long id,
        String content,
        Integer views,
        IsDeleted isDeleted,
        LocalDateTime createdAt,
        PostUserResponse user,
        List<ImageResponseDto> images,
        Long likeCount,
        Boolean isLikedByUser
) {
    public static ParentPostResponseDto from(Post post, boolean isLikedByUser) {
        return new ParentPostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),         // User 변환
                convertImagesToDtoList(post),        // Images 변환
                post.getLikesCount(),
                isLikedByUser                        // 좋아요 여부 설정
           );
}

    private static PostUserResponse convertUser(User user) {
        return user != null ? PostUserResponse.from(user) : null;
    }

    private static List<ImageResponseDto> convertImagesToDtoList(Post post) {
        return post.getImages() != null ? post.getImages().stream()
                .map(ImageResponseDto::from)
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
