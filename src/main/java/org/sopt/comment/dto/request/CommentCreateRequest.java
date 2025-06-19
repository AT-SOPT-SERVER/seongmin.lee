package org.sopt.comment.dto.request;

public record CommentCreateRequest(Long postId, String content) {
}
