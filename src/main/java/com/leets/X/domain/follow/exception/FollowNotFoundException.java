package com.leets.X.domain.follow.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.follow.exception.ErrorMessage.*;

public class FollowNotFoundException extends BaseException {
    public FollowNotFoundException() {
        super(FOLLOW_NOT_FOUND.getCode(), FOLLOW_NOT_FOUND.getMessage());
    }
}

