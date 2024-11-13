package com.leets.X.domain.user.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.user.exception.ErrorMessage.*;

public class DuplicateCustomIdException extends BaseException {
    public DuplicateCustomIdException() {
        super(DUPLICATE_CUSTOMID.getCode(), DUPLICATE_CUSTOMID.getMessage());
    }
}
