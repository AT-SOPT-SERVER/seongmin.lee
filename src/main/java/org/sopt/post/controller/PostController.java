package org.sopt.post.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.service.CommentService;
import org.sopt.global.auth.security.CustomUserDetails;
import org.sopt.post.dto.response.PostInfoListResponse;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;


    @PostMapping
//    @RateLimit(tag = "createPost")
    public ResponseEntity<ResultResponse<Void>> createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostCreateRequest postRequest
    ) {
        URI location = URI.create("/posts/" + postService.addPost(userDetails.getId(), postRequest));

        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<PostResponse>> getPostById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.getPost(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> deletePostById(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        postService.deletePost(userDetails.getId(), id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> updatePostTitle(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @RequestBody PostUpdateRequest updateRequest
    ) {
        postService.updatePost(userDetails.getId(), id, updateRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<PostInfoListResponse>> getPosts(
            @RequestHeader(required = false) Long userId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.searchPosts(
                userId,
                keyword,
                username,
                tags,
                page,
                size
        )));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ResultResponse<CommentListResponse>> getComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, commentService.getComments(
                postId,
                page,
                size
        )));
    }

}
