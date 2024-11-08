package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.image.dto.response.ImageResponse;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String content;
    private Integer views;
    private IsDeleted isDeleted;
    private LocalDateTime createdAt;
    private PostUserResponse user; // 작성자 정보
    private List<ImageResponse> images; // 관련 이미지 리스트
    private Long likeCount; // 좋아요 개수 추가

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),
                convertImagesToDtoList(post),
                post.getLikesCount() // 좋아요 개수 추가
        );
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


