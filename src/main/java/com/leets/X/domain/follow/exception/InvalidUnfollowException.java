package com.leets.X.domain.follow.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.follow.exception.ErrorMessage.*;

public class InvalidUnfollowException extends BaseException {
    public InvalidUnfollowException() {
        super(INVALID_UNFOLLOW.getCode(), INVALID_UNFOLLOW.getMessage());
    }
}
