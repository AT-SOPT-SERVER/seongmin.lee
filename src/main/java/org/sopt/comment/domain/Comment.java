package org.sopt.comment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.like.domain.CommentLike;
import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "comment", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CommentLike> likes = new ArrayList<>();

    protected Comment(){

    }

    public static Comment createComment(User user, Post post, String content){
        Comment comment = new Comment();
        user.getComments().add(comment);
        post.getComments().add(comment);
        comment.post = post;
        comment.user = user;
        comment.content = content;
        return comment;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }
}
