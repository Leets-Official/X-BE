package com.leets.X.domain.post.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.post.exception.ErrorMessage.POST_NOT_FOUND;
import static com.leets.X.domain.user.exception.ErrorMessage.USER_NOT_FOUND;

public class PostNotFoundException extends BaseException {

    public PostNotFoundException() {
        super(POST_NOT_FOUND.getCode(), POST_NOT_FOUND.getMessage());
    }




}
