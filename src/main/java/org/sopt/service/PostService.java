package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.PostListResponse;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
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
    public Long addPost(PostRequest postRequest) {
        validateTitle(postRequest.title());

        Post newPost = new Post();
        newPost.updateTitle(postRequest.title());
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostListResponse getAllPosts() {
        List<PostResponse> postList = postRepository.findAll().stream()
                .map(PostResponse::of)
                .toList();
        return PostListResponse.of(postList);
    }

    public PostResponse getPost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        return PostResponse.of(findPost);
    }

    @Transactional
    public void updatePost(Long updateId, String newTitle) {
        validateTitle(newTitle);

        Post post = postRepository.findById(updateId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        post.updateTitle(newTitle);
    }

    @Transactional
    public void deletePost(Long deleteId) {

        Post findPost = postRepository.findById(deleteId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        postRepository.delete(findPost);
    }

    public PostListResponse searchPosts(String keyword) {
        List<PostResponse> postList = postRepository.findPostsByTitleContaining(keyword)
                .stream()
                .map(PostResponse::of).toList();
        return PostListResponse.of(postList);
    }


    private void validateTitle(String title) {
        if(TextValidator.isBlank(title)) throw new BusinessException(NOT_ALLOWED_BLANK_TITLE);
        if(TextValidator.isTextLengthBiggerThanLimit(title, TITLE_LIMIT)) throw new BusinessException(TOO_LONG_TITLE);

        boolean present = postRepository.findPostByTitle(title).isPresent();
        if(present) throw new BusinessException(DUPLICATED_TITLE);
    }
}
