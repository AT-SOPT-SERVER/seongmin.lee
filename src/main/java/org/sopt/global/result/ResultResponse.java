package org.sopt.global.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ResultResponse<T> {

    private final int code;
    private final String msg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;


    public ResultResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultResponse<T> of(ResultCode resultCode, T data){
        return new ResultResponse(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
