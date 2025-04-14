package org.sopt.global.error;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String message;
    private final int httpStatus;
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode, LocalDateTime localDateTime){
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getStatus();
        this.timestamp = localDateTime;
    }

    public static ErrorResponse of(ErrorCode errorCode, LocalDateTime localDateTime){
        return new ErrorResponse(errorCode, localDateTime);
    }


    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
