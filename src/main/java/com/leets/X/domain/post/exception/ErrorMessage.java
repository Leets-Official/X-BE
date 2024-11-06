package com.leets.X.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    POST_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    UNAUTHORIZED_POST_DELETION(403, "게시물을 삭제할 권한이 없습니다."),
    ALREADY_LIKED(400, "이미 좋아요를 누른 게시물입니다."),
    NOT_LIKED(400, "좋아요가 눌려 있지 않은 게시물입니다.");
    private final int code;
    private final String message;

}
