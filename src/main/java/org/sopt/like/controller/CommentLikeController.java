package org.sopt.like.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.service.CommentService;
import org.sopt.global.auth.security.CustomUserDetails;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.like.dto.response.LikeCountResponse;
import org.sopt.like.service.CommentLikeService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/{commentId}/likes")
public class CommentLikeController {


    private final UserService userService;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;


    @PostMapping
    public ResponseEntity<ResultResponse<Void>> toggleCommentLike(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long commentId
    ){

        User user = userService.findUser(userDetails.getId());
        Comment comment = commentService.findComment(commentId);
        commentLikeService.toggleLike(user, comment);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<LikeCountResponse>> getCommentLikeCount(
            @PathVariable Long commentId
    ){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, commentLikeService.getLikeCount(commentId)));
    }
}
