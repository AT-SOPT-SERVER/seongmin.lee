package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.CommentLike;
import org.sopt.like.dto.response.LikeCountResponse;
import org.sopt.like.repository.CommentLikeRepository;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.user.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @CacheEvict(cacheNames = "commentLikeCount", key = "#comment.id")
    public void toggleLike(User user, Comment comment){
        boolean liked = commentLikeRepository.existsByUserAndComment(user, comment);
        if(liked){
            commentLikeRepository.deleteByUserAndComment(user, comment);
        }else{
            commentLikeRepository.save(CommentLike.createCommentLike(user, comment));
        }
    }

    @Cacheable(cacheNames = "commentLikeCount", key = "#commentId")
    public LikeCountResponse getLikeCount(Long commentId){
        return LikeCountResponse.of(commentLikeRepository.countByCommentId(commentId));
    }
}
