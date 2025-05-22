package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    List<Post> findPostsByTitle(String title);

    @Query("select p from Post p join fetch p.user where p.user = :user")
    List<Post> findByUser(User user);

    Page<Post> findAllByOrderByCreatedTimeDesc(Pageable pageable);
}
