package org.sopt.like.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.like.dto.response.LikeCountResponse;
import org.sopt.like.service.PostLikeService;
import org.sopt.post.domain.Post;
import org.sopt.post.service.PostService;
import org.sopt.user.domain.User;
import org.sopt.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    private final UserService userService;
    private final PostService postService;
    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<ResultResponse<Void>> togglePostLike(
            @RequestHeader(name = "Authorization") Long userId,
            @PathVariable Long postId
    ){
        User user = userService.findUser(userId);
        Post post = postService.findPost(postId);
        postLikeService.toggleLike(user, post);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<LikeCountResponse>> getPostLikeCount(
            @PathVariable Long postId
    ){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postLikeService.getLikeCount(postId)));
    }
}
