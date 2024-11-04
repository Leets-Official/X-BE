package com.leets.X.domain.follow.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.follow.exception.ErrorMessage.*;

public class AlreadyFollowException extends BaseException {
    public AlreadyFollowException() {
        super(ALREADY_FOLLOW.getCode(), ALREADY_FOLLOW.getMessage());
    }
}

