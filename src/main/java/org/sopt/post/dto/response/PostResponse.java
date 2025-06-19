package org.sopt.post.dto.response;

import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;

import java.util.List;

public record PostResponse(Long id, String title, String content, List<PostTag> tags, Long userId, String username) {

    public static PostResponse of(Post post){
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getTags(), post.getUser().getId(), post.getUser().getName());
    }
}
