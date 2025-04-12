package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.dto.PostRequest;
import org.sopt.exception.ErrorMessage;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.domain.Post.lastPostTime;
import static org.sopt.exception.ErrorMessage.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {

        if (lastPostTime == null){
            postService.addPost(postRequest.getTitle());
            lastPostTime = LocalDateTime.now();
        }else {
            Duration diff = Duration.between(LocalDateTime.now(), lastPostTime);
            long minutes = diff.toMinutes();
            if(minutes < 3) throw new IllegalStateException(ERROR_NOT_EXPIRED_YET.getMessage());

            postService.addPost(postRequest.getTitle());
            lastPostTime = LocalDateTime.now();
        }
        return ResponseEntity.ok("ok");

    }

    @GetMapping("/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    public Post getPostById(int id) {
        return postService.getPost(id);
    }

    public boolean updatePostTitle(int updateId, String newTitle) {
        return postService.updatePost(updateId, newTitle);
    }

    public boolean deletePostById(int deleteId) {
        return postService.deletePost(deleteId);
    }

    public List<Post> searchPostsByKeyword(String keyword) {
        return postService.searchPosts(keyword);
    }

    public boolean saveAsFile() throws IOException {
        return postService.saveAsFile();
    }

    public boolean loadFromFile() throws IOException {
        return postService.loadFromFile();
    }
}
