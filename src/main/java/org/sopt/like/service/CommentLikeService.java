package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.CommentLike;
import org.sopt.like.repository.CommentLikeRepository;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    public void toggleLike(User user, Comment comment){
        boolean liked = commentLikeRepository.existsByUserAndComment(user, comment);
        if(liked){
            commentLikeRepository.deleteByUserAndComment(user, comment);
        }else{
            commentLikeRepository.save(CommentLike.createCommentLike(user, comment));
        }
    }
}
