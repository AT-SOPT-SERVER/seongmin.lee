package org.sopt.repository;

import org.sopt.domain.Post;
import org.sopt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findPostByTitle(String title);

    // 페치 조인을 통해 N + 1 방지
    @Query("select p from Post p join fetch User u where p.user = :user")
    List<Post> findByUser(User user);
}
