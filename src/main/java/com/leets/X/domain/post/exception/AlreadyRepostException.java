package com.leets.X.domain.post.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.post.exception.ErrorMessage.ALREADY_REPOST;

public class AlreadyRepostException extends BaseException {

    public AlreadyRepostException() {
        super(ALREADY_REPOST.getCode(), ALREADY_REPOST.getMessage());
    }




}
