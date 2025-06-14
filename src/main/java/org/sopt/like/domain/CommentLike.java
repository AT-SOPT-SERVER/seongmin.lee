package org.sopt.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.sopt.comment.domain.Comment;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.user.domain.User;

@Entity
@Table(indexes = {
        @Index(name = "idx_comment_like_comment_id", columnList = "comment_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    public static CommentLike createCommentLike(User user, Comment comment){
        CommentLike commentLike = new CommentLike();
        commentLike.user = user;
        commentLike.comment = comment;
        comment.getLikes().add(commentLike);
        return commentLike;
    }
}
