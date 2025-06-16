package org.sopt.comment.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.comment.dto.request.CommentCreateRequest;
import org.sopt.comment.dto.request.CommentUpdateRequest;
import org.sopt.comment.dto.response.CommentListResponse;
import org.sopt.comment.repository.CommentRepository;
import org.sopt.global.error.ErrorCode;
import org.sopt.global.error.exception.BusinessException;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private static final int MAX_CONTENT_LENGTH = 300;

    public Long addComment(User user, Post post, CommentCreateRequest request){
        validateContent(request.content());
        Comment comment = Comment.createComment(user, post, request.content());
        commentRepository.save(comment);
        return comment.getId();
    }

    @CacheEvict(cacheNames = "postComments", key = "")
    public void modifyComment(Long userId, Long commentId, CommentUpdateRequest request){
        validateContent(request.newContent());
        Comment comment = findComment(commentId);
        if(!comment.getUser().getId().equals(userId)) throw new BusinessException(ErrorCode.NO_PERMISSION_TO_ACCESS_COMMENT);
        comment.updateContent(request.newContent());
    }

    public void deleteComment(Long userId, Long commentId){
        Comment comment = findComment(commentId);
        if(!comment.getUser().getId().equals(userId)) throw new BusinessException(ErrorCode.NO_PERMISSION_TO_ACCESS_COMMENT);
        commentRepository.delete(comment);
    }

    public Comment findComment(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_COT_FOUND));
    }

    public CommentListResponse getComments(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdTime"));
        return CommentListResponse.from(commentRepository.findCommentsByPostId(postId, pageable));
    }

    public void validateContent(String content){
        if(content.length() > MAX_CONTENT_LENGTH){
            throw new BusinessException(ErrorCode.TOO_LONG_COMMENT_CONTENT);
        }
    }
}
