package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.sopt.dto.PostInfoListResponse;
import org.sopt.dto.PostRequest;
import org.sopt.dto.PostResponse;
import org.sopt.dto.UpdateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.repository.PostRepository;
import org.sopt.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.sopt.global.error.ErrorCode.*;

@Service
public class PostService {

    private final int TITLE_LIMIT = 30;
    private final int CONTENT_LIMIT = 1000;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Long addPost(Long userId, PostRequest postRequest) {
        validateTitle(postRequest.title());
        validateContent(postRequest.content());

        User findUser = findUser(userId);

        Post newPost = Post.createPost(findUser, postRequest.title(), postRequest.content());
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostInfoListResponse getAllPosts(Long userId) {
        User findUser = findUser(userId);
        return PostInfoListResponse.of(postRepository.findByUser(findUser));
    }

    public PostResponse getPost(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        return PostResponse.of(findPost);
    }

    @Transactional
    public void updatePost(Long updateId, UpdateRequest updateRequest) {
        validateTitle(updateRequest.title());
        validateContent(updateRequest.content());

        Post post = postRepository.findById(updateId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        post.updateTitle(updateRequest.title(), updateRequest.content());
    }

    @Transactional
    public void deletePost(Long deleteId) {

        Post findPost = postRepository.findById(deleteId).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
        postRepository.delete(findPost);
    }

    public PostInfoListResponse searchPosts(String keyword, String username) {
        return PostInfoListResponse.of(postRepository.searchPost(keyword, username));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }


    private void validateTitle(String title) {
        if(TextValidator.isBlank(title)) throw new BusinessException(NOT_ALLOWED_BLANK_TITLE);
        if(TextValidator.isTextLengthBiggerThanLimit(title, TITLE_LIMIT)) throw new BusinessException(TOO_LONG_TITLE);
        if(isTitlePresent(title)) throw new BusinessException(DUPLICATED_TITLE);
    }

    private boolean isTitlePresent(String title) {
        return postRepository.findPostByTitle(title).isPresent();
    }

    private void validateContent(String content) {
        if(TextValidator.isBlank(content)) throw new BusinessException(NOT_ALLOWED_BLANK_CONTENT);
        // 성능 이슈가 있을 수 있음, TextValidator에서 100000 글자에 대해서 전부 count하게 될 수도 있음
        if(TextValidator.isTextLengthBiggerThanLimit(content, CONTENT_LIMIT)) throw new BusinessException(TOO_LONG_CONTENT);
    }
}
