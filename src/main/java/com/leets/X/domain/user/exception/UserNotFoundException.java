package com.leets.X.domain.user.exception;

import com.leets.X.global.common.exception.BaseException;

import static com.leets.X.domain.user.exception.ErrorMessage.*;

// 모든 사용자 정의 예외는 BaseException을 상속할 것
public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND.getCode(), USER_NOT_FOUND.getMessage());
    }

}
