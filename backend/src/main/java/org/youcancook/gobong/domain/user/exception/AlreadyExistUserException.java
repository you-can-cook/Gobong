package org.youcancook.gobong.domain.user.exception;

import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.IllegalException;

public class AlreadyExistUserException extends IllegalException {
    public AlreadyExistUserException() {
        super(ErrorCode.ALREADY_EXIST_USER);
    }
}
