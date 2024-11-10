package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.image.dto.response.ImageResponse;
import com.leets.X.domain.like.repository.LikeRepository;
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
        List<ImageResponse> images,
        Long likeCount,
        List<PostResponseDto> replies,
        Boolean isLikedByUser // 좋아요 여부 확인
) {


    public static PostResponseDto from(Post post, boolean isLikedByUser) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),
                convertImagesToDtoList(post),
                post.getLikesCount(),
                convertRepliesToDtoList(post.getReplies()),
                isLikedByUser // 서비스에서 전달된 boolean 값 사용
        );
    }


    // 좋아요 여부를 확인하기 위한 메서드 오버로딩
    public static PostResponseDto from(Post post, User user, LikeRepository likeRepository) {
        boolean isLikedByUser = user != null && likeRepository.existsByPostAndUser(post, user); // 좋아요 여부 확인

        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),
                convertImagesToDtoList(post),
                post.getLikesCount(),
                convertRepliesToDtoList(post.getReplies()),
                isLikedByUser // 좋아요 여부를 동적으로 설정
        );
    }


    private static List<PostResponseDto> convertRepliesToDtoList(List<Post> replies) {
        return replies != null ? replies.stream()
                .map(reply -> PostResponseDto.from(reply, false)) // 기본적으로 isLikedByUser를 false로 설정
                .collect(Collectors.toList()) : Collections.emptyList();
    }
    private static PostUserResponse convertUser(User user) {
        return user != null ? PostUserResponse.from(user) : null;
    }

    private static List<ImageResponse> convertImagesToDtoList(Post post) {
        return post.getImages().stream()
                .map(ImageResponse::from)
                .toList();
    }

}


