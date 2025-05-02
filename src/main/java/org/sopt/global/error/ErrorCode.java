package org.sopt.global.error;

public enum ErrorCode {

    POST_NOT_FOUND(40402, 404, "해당하는 게시물이 존재하지 않습니다."),
    NOT_ALLOWED_BLANK_TITLE(40001, 400, "제목은 비어있을 수 없습니다."),
    NOT_ALLOWED_BLANK_CONTENT(40002, 400, "내용은 비어있을 수 없습니다."),
    NOT_ALLOWED_BLANK_USERNAME(40003, 400, "닉네임은 비어있을 수 없습니다."),
    TOO_LONG_TITLE(40004, 400, "제목의 길이는 30글자를 넘어선 안됩니다."),
    TOO_LONG_CONTENT(40005, 400, "내용의 길이는 1000글자를 넘어선 안됩니다."),
    TOO_LONG_USERNAME(40006, 400, "닉네임의 길이는 10글자를 넘어선 안됩니다."),
    DUPLICATED_TITLE(40901, 409, "이미 존재하는 제목입니다."),
    NOT_EXPIRED_YET(40301, 403, "아직 유효시간이 지나지 않았습니다."),
    NOT_SUPPORTED_METHOD_ERROR(40501, 405, "잘못된 HTTP method 요청입니다."),
    NOT_FOUND_ERROR(40401, 404, "지원하지 않는 URL입니다."),
    INTERNAL_SERVER_ERROR(50001, 500, "서버 내부 오류입니다."),
    USER_NOT_FOUND(40403, 404, "해당하는 유저가 존재하지 않습니다.");

    private final int code;
    private final int status;
    private final String msg;

    ErrorCode(int code, int status, String msg) {
        this.code = code;
        this.status = status;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg(){
        return msg;
    }
}
