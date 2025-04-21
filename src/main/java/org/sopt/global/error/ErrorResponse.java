package org.sopt.global.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final int code;
    private final String msg;
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode, LocalDateTime localDateTime){
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.timestamp = localDateTime;
    }

    public static ErrorResponse of(ErrorCode errorCode, LocalDateTime localDateTime){
        return new ErrorResponse(errorCode, localDateTime);
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
