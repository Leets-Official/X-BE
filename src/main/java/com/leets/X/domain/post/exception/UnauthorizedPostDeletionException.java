package com.leets.X.domain.post.exception;

import com.leets.X.global.common.exception.BaseException;

public class UnauthorizedPostDeletionException extends BaseException {
    public UnauthorizedPostDeletionException() {
        super(ErrorMessage.UNAUTHORIZED_POST_DELETION.getCode(), ErrorMessage.UNAUTHORIZED_POST_DELETION.getMessage());
    }
}
