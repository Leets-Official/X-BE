package com.leets.X.domain.follow.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.follow.exception.ErrorMessage.*;

public class InvalidFollowException extends BaseException {
    public InvalidFollowException() {
        super(INVALID_FOLLOW.getCode(), INVALID_FOLLOW.getMessage());
    }
}
