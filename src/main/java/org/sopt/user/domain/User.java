package org.sopt.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.sopt.comment.domain.Comment;
import org.sopt.global.entity.BaseTimeEntity;
import org.sopt.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    protected User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User createUser(String name, String email) {
        return new User(name, email);
    }

}
