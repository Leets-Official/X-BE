package com.leets.X.domain.post.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    POST_SUCCESS(201, "게시물이 성공적으로 생성되었습니다."),
    GET_POST_SUCCESS(200, "게시물 조회에 성공했습니다."),
    GET_SORTED_BY_LIKES_SUCCESS(200, "좋아요 순으로 게시물 조회에 성공했습니다."),
    GET_LATEST_POST_SUCCESS(200, "최신 게시물 조회에 성공했습니다."),
    ADD_LIKE_SUCCESS(201, "좋아요가 추가되었습니다."),
    POST_DELETED_SUCCESS(200, "게시물이 성공적으로 삭제되었습니다."),
    LIKE_CANCEL_SUCCESS(200, "좋아요가 성공적으로 취소되었습니다."),
    REPLY_SUCCESS(201, "답글이 생성되었습니다.");

    private final int code;
    private final String message;
}
