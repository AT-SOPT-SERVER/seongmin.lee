package org.sopt.post.domain;

import org.sopt.global.error.exception.BusinessException;

import java.util.Optional;

import static org.sopt.global.error.ErrorCode.INVALID_POST_TAG;

public enum PostTag {
    DATABASE, BACKEND, INFRA;

    public static PostTag from(String value){
        try{
            return PostTag.valueOf(value.toUpperCase());
        }catch (IllegalArgumentException e){
            throw new BusinessException(INVALID_POST_TAG);
        }
    }
}
