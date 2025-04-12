package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {

    private List<Post> postList = new ArrayList<>();
    public void save(Post post) {
        postList.add(post);
    }

    public List<Post> findAll() {
        return this.postList;
    }

    public Post findPostById(int id) {
        return postList.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean deleteById(int deleteId) {
        return postList.removeIf(post -> post.getId() == deleteId);
    }

    public List<Post> findPostsByKeyword(String keyword) {
        return postList.stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }

    public Post findPostByTitle(String title){
        return postList.stream()
                .filter(post -> post.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

}
