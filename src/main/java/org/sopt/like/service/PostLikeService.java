package org.sopt.like.service;

import lombok.RequiredArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.CommentLike;
import org.sopt.like.domain.PostLike;
import org.sopt.like.repository.PostLikeRepository;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public void toggleLike(User user, Post post){
        boolean liked = postLikeRepository.existsByUserAndPost(user, post);
        if(liked){
            postLikeRepository.deleteByUserAndPost(user, post);
        }else{
            postLikeRepository.save(PostLike.createPostLike(user, post));
        }
    }
}
