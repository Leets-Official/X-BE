package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.user.domain.User;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public record PostResponseDto(
        Long id,
        String content,
        Integer views,
        IsDeleted isDeleted,
        LocalDateTime createdAt,
        PostUserResponse user,
        List<ImageResponseDto> images,
        Long likeCount,
        List<PostResponseDto> replies
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),
                convertImagesToDtoList(post),
                post.getLikesCount(),
                convertRepliesToDtoList(post.getReplies())
        );
    }

    private static List<PostResponseDto> convertRepliesToDtoList(List<Post> replies) {
        return replies != null ? replies.stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList()) : Collections.emptyList();
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
