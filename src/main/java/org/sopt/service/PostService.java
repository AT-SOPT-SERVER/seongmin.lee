package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.IdGeneratorUtil;
import org.sopt.validator.TitleValidator;

import java.io.IOException;
import java.util.List;

public class PostService {

    private final int TITLE_LIMIT = 30;

    private final PostRepository postRepository = new PostRepository();
    public void addPost(String title) {
        validateTitle(title);

        Post post = new Post(IdGeneratorUtil.generateId(), title);
        postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(int id) {
        return postRepository.findPostById(id);
    }

    public boolean updatePost(int updateId, String newTitle) {
        validateTitle(newTitle);

        Post post = postRepository.findPostById(updateId);
        if(post == null){
            return false;
        }
        post.setTitle(newTitle);
        return true;
    }

    public boolean deletePost(int deleteId) {
        return postRepository.deleteById(deleteId);
    }

    public List<Post> searchPosts(String keyword) {
        return postRepository.findPostsByKeyword(keyword);
    }

    private void validateTitle(String title) {
        if(TitleValidator.isBlank(title)) throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        if(TitleValidator.isExceedingTitleLimit(title, TITLE_LIMIT)) throw new IllegalArgumentException("제목은 30글자를 초과해선 안됩니다.");

        Post findPost = postRepository.findPostByTitle(title);
        if(findPost != null) throw new IllegalStateException("이미 존재하는 제목입니다.");
    }

    public boolean saveAsFile() throws IOException {
        return postRepository.saveAsFile();
    }

    public boolean loadFromFile() throws IOException {
        return postRepository.loadFromFile();
    }
}
