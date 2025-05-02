package org.sopt.post.service;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostTag;
import org.sopt.user.domain.User;
import org.sopt.post.dto.PostInfoListResponse;
import org.sopt.post.dto.PostCreateRequest;
import org.sopt.post.dto.PostResponse;
import org.sopt.post.dto.PostUpdateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Long addPost(Long userId, PostCreateRequest postRequest) {
        validateTitle(postRequest.title());
        validateContent(postRequest.content());

        User findUser = findUser(userId);

        Post newPost = Post.createPost(findUser, postRequest.title(), postRequest.content(), postRequest.tag());
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostInfoListResponse getAllPosts(Long userId) {
        User findUser = findUser(userId);
        return PostInfoListResponse.of(postRepository.findByUser(findUser));
    }

    public PostResponse getPost(Long id) {
        Post findPost = findPost(id);
        return PostResponse.of(findPost);
    }

    @Transactional
    public void updatePost(Long updateId, PostUpdateRequest updateRequest) {
        validateTitle(updateRequest.title());
        validateContent(updateRequest.content());

        Post findPost = findPost(updateId);
        findPost.updatePost(updateRequest.title(), updateRequest.content(), updateRequest.tag());
    }

    @Transactional
    public void deletePost(Long deleteId) {
        Post findPost = findPost(deleteId);
        postRepository.delete(findPost);
    }


    public PostInfoListResponse searchPosts(String keyword, String username, String tag) {
        PostTag postTag = PostTag.from(tag);
        return PostInfoListResponse.of(postRepository.searchPost(keyword, username, postTag));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }


    private void validateTitle(String title) {
        if(TextValidator.isBlank(title)) throw new BusinessException(NOT_ALLOWED_BLANK_TITLE);
        if(TextValidator.isTextLengthBiggerThanLimit(title, TITLE_LIMIT)) throw new BusinessException(TOO_LONG_TITLE);
        if(isTitlePresent(title)) throw new BusinessException(DUPLICATED_TITLE);
    }

    private void validateContent(String content) {
        if(TextValidator.isBlank(content)) throw new BusinessException(NOT_ALLOWED_BLANK_CONTENT);
        if(TextValidator.isTextLengthBiggerThanLimit(content, CONTENT_LIMIT)) throw new BusinessException(TOO_LONG_CONTENT);
    }

    private boolean isTitlePresent(String title) {
        return !postRepository.findPostsByTitle(title).isEmpty();
    }
}
