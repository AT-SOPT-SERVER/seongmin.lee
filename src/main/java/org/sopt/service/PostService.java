package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGeneratorUtil;
import org.sopt.validator.TitleValidator;

import java.util.List;

public class PostService {

    private final int TITLE_LIMIT = 30;

    private final PostRepository postRepository = new PostRepository();
    public void addPost(String title) {
        if(TitleValidator.isBlank(title)) return;
        if(TitleValidator.isExceedingTitleLimit(title, TITLE_LIMIT)) return;

        Post findPost = postRepository.findPostByTitle(title);
        if(findPost != null) return;

        Post post = new Post(IdGeneratorUtil.generateId(), title);
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(int id) {
        return postRepository.findPostById(id);
    }

    public boolean updatePost(int updateId, String newTitle) {
        Post post = postRepository.findPostById(updateId);
        if(post == null){
            return false;
        }
        post.setTitle(newTitle);
        return true;
    }

    public boolean deletePost(int deleteId) {
        return postRepository.deleteById(deleteId);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findPostsByKeyword(keyword);
    }
}
