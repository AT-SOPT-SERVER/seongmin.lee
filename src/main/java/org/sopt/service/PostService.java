package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.repository.PostRepository;
import org.sopt.validator.TextValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.sopt.global.error.ErrorCode.*;

@Service
public class PostService {

    private final int TITLE_LIMIT = 30;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long addPost(String title) {
        validateTitle(title);

        Post newPost = new Post();
        newPost.setTitle(title);
        postRepository.save(newPost);

        return newPost.getId();
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
    }

    @Transactional
    public void updatePost(Long updateId, String newTitle) {
        validateTitle(newTitle);

        Post post = postRepository.findById(updateId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        post.setTitle(newTitle);
    }

    @Transactional
    public void deletePost(Long deleteId) {

        Post findPost = postRepository.findById(deleteId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        postRepository.delete(findPost);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findPostsByTitleContaining(keyword);
    }


    private void validateTitle(String title) {
        if(TextValidator.isBlank(title)) throw new BusinessException(NOT_ALLOWED_BLANK_TITLE);
        if(TextValidator.isTextLengthBiggerThanLimit(title, TITLE_LIMIT)) throw new BusinessException(TOO_LONG_TITLE);

        boolean present = postRepository.findPostByTitle(title).isPresent();
        if(present) throw new BusinessException(DUPLICATED_TITLE);
    }
}
