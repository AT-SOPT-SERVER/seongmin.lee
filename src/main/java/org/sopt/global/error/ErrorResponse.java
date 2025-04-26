package org.sopt.global.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final int code;
    private final String msg;

    public ErrorResponse(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public static ErrorResponse of(ErrorCode errorCode){
        return new ErrorResponse(errorCode);
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
