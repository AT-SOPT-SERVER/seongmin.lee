package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.sopt.domain.Post.lastPostTime;

public class PostController {

    private final PostService postService = new PostService();

    public void createPost(String title) {

        if (lastPostTime == null){
            postService.addPost(title);
            lastPostTime = LocalDateTime.now();
        }else {
            Duration diff = Duration.between(LocalDateTime.now(), lastPostTime);
            long minutes = diff.toMinutes();
            if(minutes < 3) throw new IllegalStateException("마지막 요청 이후 3분이 지나지 않았습니다.");

            postService.addPost(title);
            lastPostTime = LocalDateTime.now();
        }

    }

    public List<Post> getAllPosts() {
        return postService.getAllPosts();
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
