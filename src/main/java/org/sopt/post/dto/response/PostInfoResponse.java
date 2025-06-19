package org.sopt.post.dto.response;

import org.sopt.post.domain.Post;

public record PostInfoResponse(Long postId, String title, String username) {
    public static PostInfoResponse from(Post post){
        return new PostInfoResponse(post.getId(), post.getTitle(), post.getUser().getName());
    }
}
