package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.post.domain.Post;
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
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private UserResponseDto user; // 작성자 정보
    private List<ImageResponseDto> images; // 관련 이미지 리스트
    private List<CommentResponseDto> comments; // 관련 댓글 리스트

    public static PostResponseDto from(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getViews(),
                post.getIsDeleted(),
                post.getCreatedAt(),
                UserResponseDto.from(post.getUser()), // UserResponseDto 변환
                post.getImages().stream().map(ImageResponseDto::from).toList(), // 이미지 리스트 변환
                post.getCommentList().stream().map(CommentResponseDto::from).toList() // 댓글 리스트 변환
        );
    }
}
