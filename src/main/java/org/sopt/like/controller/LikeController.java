package org.sopt.like.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.service.CommentService;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.like.service.CommentLikeService;
import org.sopt.like.service.PostLikeService;
import org.sopt.post.domain.Post;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final PostLikeService postLikeService;
    private final CommentLikeService commentLikeService;

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<ResultResponse<Void>> togglePostLike(
            @RequestHeader(name = "Authorization") Long userId,
            @PathVariable Long postId
    ){
        User user = userService.findUser(userId);
        Post post = postService.findPost(postId);
        postLikeService.toggleLike(user, post);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<ResultResponse<Void>> toggleCommentLike(
            @RequestHeader(name = "Authorization") Long userId,
            @PathVariable Long commentId
    ){

        User user = userService.findUser(userId);
        Comment comment = commentService.findComment(commentId);
        commentLikeService.toggleLike(user, comment);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

}
