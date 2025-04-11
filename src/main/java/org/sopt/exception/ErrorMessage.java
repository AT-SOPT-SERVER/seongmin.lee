package org.sopt.exception;

public enum ErrorMessage {

    NOT_ALLOWED_BLANK_TITLE("제목은 비어있을 수 없습니다."),
    TOO_LONG_TITLE("제목은 30글자를 초과해선 안됩니다."),
    DUPLICATED_TITLE("이미 존재하는 제목입니다."),
    ERROR_NOT_EXPIRED_YET("아직 유효시간이 지나지 않았습니다.");

    private final String message;

    ErrorMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
