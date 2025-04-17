package org.sopt.global.error;

public enum ErrorCode {

    POST_NOT_FOUND(404, "해당하는 게시물이 존재하지 않습니다."),
    NOT_ALLOWED_BLANK_TITLE(400, "제목은 비어있을 수 없습니다."),
    TOO_LONG_TITLE(400, "제목의 길이는 30글자를 넘어선 안됩니다."),
    DUPLICATED_TITLE(400, "이미 존재하는 제목입니다."),
    ERROR_NOT_EXPIRED_YET(403, "아직 유효시간이 지나지 않았습니다.");

    private final int status;
    private final String message;

    ErrorCode(int status, String message){
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage(){
        return message;
    }
}
