package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.repository.PostRepository;
import org.sopt.util.FileUtil;
import org.sopt.util.IdGeneratorUtil;
import org.sopt.validator.TitleValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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

    public boolean saveAsFile() throws IOException {
        List<Post> postList = postRepository.findAll();
        StringBuilder sb = new StringBuilder();

        postList.forEach(post -> sb.append(post.toString()).append("\n"));

        return FileUtil.saveContentAsFile(sb.toString());
    }

    public boolean loadFromFile() throws IOException {
        String content = FileUtil.loadContentFromFile();

        List<Post> postList = postRepository.findAll();

        postList.clear();

        BufferedReader reader = new BufferedReader(new StringReader(content));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.isBlank()) continue; // 빈 줄 스킵

            String[] parts = line.split(" : ", 2);
            if (parts.length < 2) continue;

            int id = Integer.parseInt(parts[0]);
            String title = parts[1];

            Post post = new Post(id, title);
            postList.add(post);
        }

        return true;
    }

    private void validateTitle(String title) {
        if(TitleValidator.isBlank(title)) throw new IllegalArgumentException("제목은 비어있을 수 없습니다.");
        if(TitleValidator.isExceedingTitleLimit(title, TITLE_LIMIT)) throw new IllegalArgumentException("제목은 30글자를 초과해선 안됩니다.");

        Post findPost = postRepository.findPostByTitle(title);
        if(findPost != null) throw new IllegalStateException("이미 존재하는 제목입니다.");
    }
}
