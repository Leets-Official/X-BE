package com.leets.X.domain.post.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    POST_NOT_FOUND(404,"존재하지 않는 게시글입니다.");

    private final int code;
    private final String message;

}
