package org.sopt.global.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String msg;
    private final int code;
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode, LocalDateTime localDateTime){
        this.msg = errorCode.getMsg();
        this.code = errorCode.getStatus();
        this.timestamp = localDateTime;
    }

    public static ErrorResponse of(ErrorCode errorCode, LocalDateTime localDateTime){
        return new ErrorResponse(errorCode, localDateTime);
    }


    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
