package org.sopt.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

@Entity
@Table(indexes = {
        @Index(name = "idx_post_like_post_id", columnList = "post_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    public static PostLike createPostLike(User user, Post post){
        PostLike postLike = new PostLike();
        postLike.user = user;
        postLike.post = post;
        post.getLikes().add(postLike);
        return postLike;
    }
}
