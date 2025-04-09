package org.sopt.controller;

import org.sopt.domain.Post;
import org.sopt.service.PostService;

import java.util.List;

public class PostController {

    private final PostService postService = new PostService();


    public void createPost(String title) {
        postService.addPost(title);
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
}
