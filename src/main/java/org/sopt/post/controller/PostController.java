package org.sopt.post.controller;

import org.sopt.aop.RateLimit;
import org.sopt.post.dto.PostInfoListResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
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
    public ResponseEntity<ResultResponse<Void>> createPost(@RequestHeader Long userId, @RequestBody PostCreateRequest postRequest) {
        URI location = URI.create("/posts/" + postService.addPost(userId, postRequest));

        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultResponse<PostResponse>> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.getPost(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> deletePostById(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultResponse<Void>> updatePostTitle(@PathVariable Long id, @RequestBody PostUpdateRequest updateRequest) {
        postService.updatePost(id, updateRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping
    public ResponseEntity<ResultResponse<PostInfoListResponse>> getAllPosts(@RequestHeader Long userId) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.getAllPosts(userId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ResultResponse<PostInfoListResponse>> searchPostsByKeyword(@RequestParam String keyword, @RequestParam String username, @RequestParam String tag) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.searchPosts(keyword, username, tag)));
    }

}
