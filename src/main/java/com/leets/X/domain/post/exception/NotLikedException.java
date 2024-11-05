package com.leets.X.domain.post.exception;

import com.leets.X.global.common.exception.BaseException;

public class NotLikedException extends BaseException {
  public NotLikedException() {
    super(ErrorMessage.NOT_LIKED.getCode(), ErrorMessage.NOT_LIKED.getMessage());
  }
}
