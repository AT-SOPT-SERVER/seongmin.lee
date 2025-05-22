package org.sopt.global.error.exception;

import lombok.Getter;
import org.sopt.global.error.ErrorCode;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

}
