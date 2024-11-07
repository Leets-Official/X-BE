package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.comment.domain.Comment;

public record CommentResponseDto(
        Long commentId,
        String content
) {
    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent());
    }
}
