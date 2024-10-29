package com.leets.X.domain.post.dto.response;

import com.leets.X.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String content;

    public static CommentResponseDto from(Comment comment) {
        return new CommentResponseDto(comment.getId(), comment.getContent());
    }
}
