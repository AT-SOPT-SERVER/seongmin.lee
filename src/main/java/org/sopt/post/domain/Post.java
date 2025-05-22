package org.sopt.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.sopt.comment.domain.Comment;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.like.domain.PostLike;
import org.sopt.post.domain.enums.PostTag;
import org.sopt.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(indexes = @Index(name = "idx_post_created_time", columnList = "createdTime DESC"))
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PostTag tag;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }


    public void updatePost(String newTitle, String newContent, String tag) {
        this.title = newTitle;
        this.content = newContent;
        this.tag = PostTag.from(tag);
    }

    public static Post createPost(User findUser, String title, String content, String tag) {
        Post newPost = new Post();
        newPost.title = title;
        newPost.content = content;
        newPost.tag = PostTag.from(tag);
        newPost.user = findUser;
        findUser.getPostList().add(newPost);
        return newPost;
    }

}
