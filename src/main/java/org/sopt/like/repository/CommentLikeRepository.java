package org.sopt.like.repository;

import org.sopt.comment.domain.Comment;
import org.sopt.like.domain.CommentLike;
import org.sopt.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByUserAndComment(User user, Comment comment);
    void deleteByUserAndComment(User user, Comment comment);
}
