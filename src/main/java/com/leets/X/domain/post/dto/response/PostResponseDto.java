package com.leets.X.domain.post.dto.response;


import com.leets.X.domain.comment.domain.Comment;
import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.post.domain.enums.IsDeleted;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
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
                post.getUser() != null ? UserResponseDto.from(post.getUser()) : null, // null의 여부를 확인하여 null 일 떄 적절히 처리가능
                (post.getImages() != null ? post.getImages() : List.<Image>of())
                        .stream().map(ImageResponseDto::from).toList(), // 이미지 리스트 변환
                (post.getCommentList() != null ? post.getCommentList() : List.<Comment>of())
                        .stream().map(CommentResponseDto::from).toList() // 댓글 리스트 변환
        );
    }

}
