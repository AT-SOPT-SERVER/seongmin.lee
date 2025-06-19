package org.sopt.comment.facade;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.service.CommentService;
import org.sopt.post.domain.Post;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class CommentManagementFacade {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    public Long createComment(Long userId, CommentCreateRequest request){
        User user = userService.findUser(userId);
        Post post = postService.findPost(request.postId());
        return commentService.addComment(user, post, request);
    }

    public void updateComment(Long userId, Long commentId, CommentUpdateRequest request){
        commentService.modifyComment(userId, commentId, request);
    }

    public void removeComment(Long userId, Long commentId){
        commentService.deleteComment(userId, commentId);
    }
}
