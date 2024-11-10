package com.leets.X.domain.image.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    S3_UPLOAD_FAIL(500, "이미지 업로드 중 에러가 발생했습니다.");

    private final int code;
    private final String message;

}
