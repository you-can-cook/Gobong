package org.youcancook.gobong.domain.follow.exception;

import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.IllegalException;

public class NotFollowException extends IllegalException {
    public NotFollowException() {
        super(ErrorCode.NOT_FOLLOW);
    }
}
