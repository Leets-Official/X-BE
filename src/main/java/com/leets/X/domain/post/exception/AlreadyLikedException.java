package com.leets.X.domain.post.exception;

import com.leets.X.global.common.exception.BaseException;

public class AlreadyLikedException extends BaseException {
    public AlreadyLikedException() {
        super(ErrorMessage.ALREADY_LIKED.getCode(), ErrorMessage.ALREADY_LIKED.getMessage());
    }

}
