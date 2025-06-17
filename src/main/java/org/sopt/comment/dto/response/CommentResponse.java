package org.sopt.comment.dto.response;

import org.sopt.comment.domain.Comment;

public record CommentResponse(Long commentId, Long userId, String username, String content) {
    public static CommentResponse from(Comment comment){
        return new CommentResponse(comment.getId(), comment.getUser().getId(), comment.getUser().getName(), comment.getContent());
    }
}
