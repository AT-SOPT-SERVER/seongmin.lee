package org.sopt.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;
import org.sopt.comment.domain.Comment;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.like.domain.PostLike;
import org.sopt.post.domain.enums.PostTag;
import org.sopt.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(
        indexes = {
                @Index(name = "idx_post_user_id", columnList = "user_id"),
                @Index(name = "idx_post_created_time", columnList = "createdTime DESC"),
                @Index(name = "idx_post_user_id_created_time", columnList = "user_id, createdTime DESC")
        }
)
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

    @ElementCollection
    @CollectionTable(joinColumns = @JoinColumn(name = "post_id"))
    @BatchSize(size = 50)
    @Enumerated(EnumType.STRING)
    private List<PostTag> tags;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PostLike> likes = new ArrayList<>();

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }


    public void updatePost(String newTitle, String newContent, List<PostTag> tags) {
        this.title = newTitle;
        this.content = newContent;
        this.tags = tags;
    }

    public static Post createPost(User findUser, String title, String content, List<PostTag> tags) {
        Post newPost = new Post();
        newPost.title = title;
        newPost.content = content;
        newPost.tags = tags;
        newPost.user = findUser;
        findUser.getPostList().add(newPost);
        return newPost;
    }

}
