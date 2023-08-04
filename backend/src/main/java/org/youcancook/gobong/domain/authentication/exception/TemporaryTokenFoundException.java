package org.youcancook.gobong.domain.authentication.exception;

import org.youcancook.gobong.global.error.ErrorCode;
import org.youcancook.gobong.global.error.exception.NotFoundException;

public class TemporaryTokenFoundException extends NotFoundException {
    public TemporaryTokenFoundException() {
        super(ErrorCode.TEMPORARY_TOKEN_NOT_FOUND);
    }
}
