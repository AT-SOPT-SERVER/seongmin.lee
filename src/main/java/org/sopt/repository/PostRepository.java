package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private List<Post> postList = new ArrayList<>();
    public void save(Post post) {
        postList.add(post);
    }

    public List<Post> findAll() {
        return this.postList;
    }

    public Post findPostById(int id) {
        for (Post post : postList) {
            if(post.getId() == id){
                return post;
            }
        }
        return null;
    }

    public boolean deleteById(int deleteId) {
        for (Post post : postList) {
            if(post.getId() == deleteId){
                postList.remove(post);
                return true;
            }
        }
        return false;
    }

    public List<Post> findPostsByKeyword(String keyword) {
        return postList.stream()
                .filter(post -> {
                    return post.getTitle().contains(keyword);
                }).toList();
    }
}
