package org.sopt.comment.dto.response;

import org.sopt.comment.domain.Comment;

public record CommentResponse(Long commentId, String username, String content, int likeCount) {
    public static CommentResponse from(Comment comment){
        return new CommentResponse(comment.getId(), comment.getUser().getName(), comment.getContent(), comment.getLikes().size());
    }
}
