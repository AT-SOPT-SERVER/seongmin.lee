package org.sopt.post.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.response.CommentResponse;
import org.sopt.global.error.ErrorCode;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;
import org.sopt.user.domain.User;
import org.sopt.post.dto.response.PostInfoListResponse;
import org.sopt.post.dto.request.PostCreateRequest;
import org.sopt.post.dto.response.PostResponse;
import org.sopt.post.dto.request.PostUpdateRequest;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.repository.UserRepository;
import org.sopt.validator.TextValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.sopt.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MAX_CONTENT_LENGTH = 1000;
    private static final int MAX_TAGS_PER_POST = 2;

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long addPost(Long userId, PostCreateRequest postRequest) {
        validateTitle(postRequest.title());
        validateContent(postRequest.content());
        validateTags(postRequest.tags());

        User findUser = findUser(userId);
        List<PostTag> tags = getPostTags(postRequest.tags());

        Post newPost = Post.createPost(findUser, postRequest.title(), postRequest.content(), tags);
        postRepository.save(newPost);

        return newPost.getId();
    }

    public PostResponse getPost(Long id) {
        return PostResponse.of(findPost(id));
    }

    @Transactional
    public void updatePost(Long updateId, PostUpdateRequest updateRequest) {
        validateTitle(updateRequest.title());
        validateContent(updateRequest.content());
        validateTags(updateRequest.tags());

        List<PostTag> tags = getPostTags(updateRequest.tags());

        Post findPost = findPost(updateId);
        findPost.updatePost(updateRequest.title(), updateRequest.content(), tags);
    }

    @Transactional
    public void deletePost(Long deleteId) {
        Post findPost = findPost(deleteId);
        postRepository.delete(findPost);
    }


    @Transactional(readOnly = true)
    public PostInfoListResponse searchPosts(Long userId, String keyword, String username, List<String> tags, int page, int size) {
        List<PostTag> postTags = getPostTags(tags);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        return PostInfoListResponse.from(postRepository.searchPost(userId, keyword, username, postTags, pageable));
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
    }


    private void validateTitle(String title) {
        if(TextValidator.isBlank(title)) throw new BusinessException(NOT_ALLOWED_BLANK_TITLE);
        if(TextValidator.isTextLengthBiggerThanLimit(title, MAX_TITLE_LENGTH)) throw new BusinessException(TOO_LONG_POST_TITLE);
        if(isTitlePresent(title)) throw new BusinessException(DUPLICATED_TITLE);
    }

    private void validateContent(String content) {
        if(TextValidator.isBlank(content)) throw new BusinessException(NOT_ALLOWED_BLANK_CONTENT);
        if(TextValidator.isTextLengthBiggerThanLimit(content, MAX_CONTENT_LENGTH)) throw new BusinessException(TOO_LONG_POST_CONTENT);
    }

    private void validateTags(List<String> tags) {
        if(tags == null || tags.isEmpty() || tags.size() > MAX_TAGS_PER_POST) throw new BusinessException(ErrorCode.TAGS_STRUCTURE_ERROR);
    }

    private List<PostTag> getPostTags(List<String> tags) {
        return tags.stream()
                .map(PostTag::from)
                .toList();
    }

    private boolean isTitlePresent(String title) {
        return postRepository.existsByTitle(title);
    }
}
