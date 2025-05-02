package org.sopt.user.domain;

import jakarta.persistence.*;
import org.sopt.post.domain.Post;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    protected User() {
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static User createUser(String name, String email) {
        return new User(name, email);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Post> getPostList(){
        return postList;
    }
}
