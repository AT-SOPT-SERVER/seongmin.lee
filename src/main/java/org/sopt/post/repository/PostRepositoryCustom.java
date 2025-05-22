package org.sopt.post.repository;

import org.sopt.post.domain.Post;
import org.sopt.post.domain.PostTag;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> searchPost(String title, String username, PostTag tag);
}
