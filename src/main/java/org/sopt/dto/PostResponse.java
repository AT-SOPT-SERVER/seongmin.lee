package org.sopt.dto;

import org.sopt.domain.Post;

public record PostResponse(Long id, String title) {

    public static PostResponse of(Post post){
        return new PostResponse(post.getId(), post.getTitle());
    }
}
