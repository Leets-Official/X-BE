package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.comment.domain.Comment;
import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import com.leets.X.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
    private List<ImageResponseDto> images; // 관련 이미지 리스트
    private Long likeCount; // 좋아요 개수 추가
    private List<PostResponseDto> replies; // 답글 추가

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                convertUser(post.getUser()),
                convertImagesToDtoList(post),
                post.getLikesCount(), // 좋아요 개수 추가
                convertRepliesToDtoList(post.getReplies())
        );
    }


    private static List<PostResponseDto> convertRepliesToDtoList(List<Post> replies) {
        return replies != null ? replies.stream()
                .map(PostResponseDto::from) // 각 답글을 재귀적으로 변환
                .collect(Collectors.toList()) : Collections.emptyList();
    }

    private static PostUserResponse convertUser(User user) {
        return user != null ? PostUserResponse.from(user) : null;
    }

    //이미지 널체킹 조건 추가
    private static List<ImageResponseDto> convertImagesToDtoList(Post post) {
        if (post.getImages() == null) {
            return Collections.emptyList(); // 이미지 리스트가 null인 경우 빈 리스트 반환
        }
        return post.getImages().stream()
                .map(ImageResponseDto::from)
                .collect(Collectors.toList());
    }


}


