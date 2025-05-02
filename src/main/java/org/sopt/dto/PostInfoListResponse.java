package org.sopt.dto;

import org.sopt.domain.Post;

import java.util.List;

public record PostInfoListResponse(List<PostInfo> postList) {

    public static PostInfoListResponse of(List<Post> postList){
        return new PostInfoListResponse(postList.stream()
                .map(post -> new PostInfo(post.getTitle(), post.getUser().getName()))
                .toList()
        );
    }
}
