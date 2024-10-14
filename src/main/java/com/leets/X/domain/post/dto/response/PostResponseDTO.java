package com.leets.X.domain.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDTO {
    private Long id;
    private String content;
    private Integer views;
    private Boolean isDeleted;
    private Integer likesCount;
    private LocalDateTime createdAt;

    public PostResponseDTO(Long id, String content, Integer views, Boolean isDeleted, Integer likesCount, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.views = views;
        this.isDeleted = isDeleted;
        this.likesCount = likesCount;
        this.createdAt = createdAt;
    }
}
