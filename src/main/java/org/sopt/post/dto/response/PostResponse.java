package org.sopt.post.dto.response;

import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;

import java.util.List;

public record PostResponse(Long id, String title, String content, List<PostTag> tags, Long userId, String username, int likeCount, List<CommentResponse> comments) {

    // 게시물 좋아요 개수 처리 추가 구현 해야함
    public static PostResponse of(Post post, List<CommentResponse> comments){
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getTags(), post.getUser().getId(), post.getUser().getName(), post.getLikes().size(), comments);
    }
}
