package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<Post> searchPost(Long userId, String title, String username, List<PostTag> tags, Pageable pageable);
}
