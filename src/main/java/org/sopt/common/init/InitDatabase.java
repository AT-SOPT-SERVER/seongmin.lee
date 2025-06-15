package org.sopt.common.init;

import lombok.RequiredArgsConstructor;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;
import org.sopt.post.repository.PostRepository;
import org.sopt.user.domain.User;
import org.sopt.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDatabase implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        User mockUser1 = User.createUser("이성민", "lee@gmail.com");
        userRepository.save(mockUser1);
        List<PostTag> tags = new ArrayList<>();
        tags.add(PostTag.ETC);
        tags.add(PostTag.DATABASE);

        Post post = Post.createPost(mockUser1, "와우", "wow", tags);
        postRepository.save(post);
    }
}
