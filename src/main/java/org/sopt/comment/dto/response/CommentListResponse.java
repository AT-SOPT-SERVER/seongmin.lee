package org.sopt.comment.dto.response;

import org.sopt.comment.domain.Comment;
import org.sopt.global.result.PagedResponse;
import org.springframework.data.domain.Page;

public record CommentListResponse(
        PagedResponse<CommentResponse> data
        ) {
    public static CommentListResponse from(Page<Comment> comments){
        return new CommentListResponse(PagedResponse.from(comments.map(CommentResponse::from)));
    }
}
