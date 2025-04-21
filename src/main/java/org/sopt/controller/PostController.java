package org.sopt.controller;

import org.sopt.aop.RateLimit;
import org.sopt.dto.PostRequest;
import org.sopt.dto.UpdateRequest;
import org.sopt.global.result.ResultCode;
import org.sopt.global.result.ResultResponse;
import org.sopt.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
//    @RateLimit
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        URI location = URI.create("/posts/" + postService.addPost(postRequest.title()));

        return ResponseEntity.created(location)
                .body(ResultResponse.of(ResultCode.CREATED, null));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.getPost(id)));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<?> updatePostTitle(@PathVariable Long id, @RequestBody UpdateRequest updateRequest) {
        postService.updatePost(id, updateRequest.title());
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, null));
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.getAllPosts()));
    }

    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPostsByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SUCCESS, postService.searchPosts(keyword)));
    }

}
