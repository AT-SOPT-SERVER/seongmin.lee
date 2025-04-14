package org.sopt.controller;

import org.sopt.aop.RateLimit;
import org.sopt.dto.PostRequest;
import org.sopt.dto.UpdateRequest;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    @RateLimit
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        postService.addPost(postRequest.getTitle());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("ok");
    }

    @PutMapping("/post")
    public ResponseEntity<?> updatePostTitle(@RequestBody UpdateRequest updateRequest) {
        postService.updatePost(updateRequest.id(), updateRequest.title());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/posts/search")
    public ResponseEntity<?> searchPostsByKeyword(@RequestParam String keyword) {
        return ResponseEntity.ok(postService.searchPosts(keyword));
    }

}
