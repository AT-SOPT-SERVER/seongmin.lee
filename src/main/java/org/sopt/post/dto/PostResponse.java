package org.sopt.post.dto;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostTag;

public record PostResponse(Long id, String title, String content, PostTag tag, Long userId, String username) {

    public static PostResponse of(Post post){
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getTag(), post.getUser().getId(), post.getUser().getName());
    }
}
