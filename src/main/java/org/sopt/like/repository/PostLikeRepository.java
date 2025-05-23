package org.sopt.like.repository;

import org.sopt.like.domain.PostLike;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
}
