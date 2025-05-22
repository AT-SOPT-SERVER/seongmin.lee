package org.sopt.post.domain.enums;

import org.sopt.global.error.exception.BusinessException;

import static org.sopt.global.error.ErrorCode.INVALID_POST_TAG;

public enum PostTag {
    DATABASE, BACKEND, INFRA;

    public static PostTag from(String value){
        if(value == null) return null;
        try{
            return PostTag.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new BusinessException(INVALID_POST_TAG);
        }
    }
}
