package com.leets.X.domain.post.exception;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() {
        super("해당 게시글을 찾을 수 없습니다.");
    }

    // 사용자 정의 메시지를 받을 수 있는 생성자
    public PostNotFoundException(String message) {
        super(message);
    }

}
