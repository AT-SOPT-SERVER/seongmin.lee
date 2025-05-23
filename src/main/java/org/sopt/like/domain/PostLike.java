package org.sopt.like.domain;

import jakarta.persistence.*;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

@Entity
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    protected PostLike(){

    }

    public static PostLike createPostLike(User user, Post post){
        PostLike postLike = new PostLike();
        postLike.user = user;
        postLike.post = post;
        post.getLikes().add(postLike);
        return postLike;
    }
}
