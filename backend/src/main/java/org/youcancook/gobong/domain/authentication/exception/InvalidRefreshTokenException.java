package org.youcancook.gobong.domain.authentication.exception;

import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.IllegalException;
import org.youcancook.gobong.global.error.exception.NotFoundException;

public class InvalidRefreshTokenException extends IllegalException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
