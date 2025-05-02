package org.sopt.domain;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private PostTag tag;

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }
    public String getContent(){
        return content;
    }

    public User getUser(){
        return user;
    }
    public PostTag getTag() {
        return tag;
    }

    public void updateTitle(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    public static Post createPost(User findUser, String title, String content) {
        Post newPost = new Post();
        newPost.title = title;
        newPost.content = content;
        newPost.user = findUser;
        findUser.getPostList().add(newPost);
        return newPost;
    }

}
