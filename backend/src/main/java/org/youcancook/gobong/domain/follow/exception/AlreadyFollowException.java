package org.youcancook.gobong.domain.follow.exception;

import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.IllegalException;

public class AlreadyFollowException extends IllegalException {
    public AlreadyFollowException() {
        super(ErrorCode.ALREADY_FOLLOW);
    }
}
