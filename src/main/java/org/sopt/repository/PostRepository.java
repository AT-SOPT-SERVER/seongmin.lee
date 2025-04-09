package org.sopt.repository;

import org.sopt.domain.Post;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository {

    private final String FILE_PATH = "src/main/java/org/sopt/assets/Post.txt";

    private List<Post> postList = new ArrayList<>();
    public void save(Post post) {
        postList.add(post);
    }

    public List<Post> findAll() {
        return this.postList;
    }

    public Post findPostById(int id) {
        for (Post post : postList) {
            if(post.getId() == id){
                return post;
            }
        }
        return null;
    }

    public boolean deleteById(int deleteId) {
        for (Post post : postList) {
            if(post.getId() == deleteId){
                postList.remove(post);
                return true;
            }
        }
        return false;
    }

    public List<Post> findPostsByKeyword(String keyword) {
        return postList.stream()
                .filter(post -> post.getTitle().contains(keyword))
                .toList();
    }

    public Post findPostByTitle(String title){
        return postList.stream()
                .filter(post -> post.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public boolean saveAsFile() throws IOException {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {

            for (Post post : postList) {
                writer.write(post.toString());
                writer.newLine();
            }
        }
        return true;
    }

    public boolean loadFromFile() throws IOException {
        File file = new File(FILE_PATH);

        if(!file.exists()){
            throw new IOException("파일이 존재하지 않습니다.");
        }

        postList.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ", 2);
                if (parts.length == 2) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    Post post = new Post(id, title);
                    postList.add(post);
                }
            }
        }

        return true;
    }
}
