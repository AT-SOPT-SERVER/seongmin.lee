package org.sopt.dto;

import org.sopt.domain.Post;

import java.util.List;

public record PostListResponse(List<PostResponse> postList) {

    public static PostListResponse of(List<PostResponse> postResponseList){
        return new PostListResponse(postResponseList);
    }
}
