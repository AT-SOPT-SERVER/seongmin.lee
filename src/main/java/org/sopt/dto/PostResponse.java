package org.sopt.dto;

import org.sopt.domain.Post;

public record PostResponse(Long id, String title, String content, Long userId, String username) {

    public static PostResponse of(Post post){
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getUser().getName());
    }
}
