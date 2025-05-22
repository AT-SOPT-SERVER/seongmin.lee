package org.sopt.post.controller;

import org.sopt.post.dto.response.PostInfoListResponse;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
//    @RateLimit(tag = "createPost")
    public ResponseEntity<ResultResponse<Void>> createPost(
            @RequestHeader Long userId,
            @RequestBody PostCreateRequest postRequest
    ) {
        URI location = URI.create("/posts/" + postService.addPost(userId, postRequest));

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
            @PathVariable Long id
    ) {
        postService.deletePost(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> updatePostTitle(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest updateRequest
    ) {
        postService.updatePost(id, updateRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<PostInfoListResponse>> getPosts(
            @RequestHeader(required = false) Long userId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String tag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.searchPosts(
                userId,
                keyword,
                username,
                tag,
                page,
                size
        )));
    }

}
