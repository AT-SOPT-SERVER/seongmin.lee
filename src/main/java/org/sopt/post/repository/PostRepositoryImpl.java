package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.sopt.post.domain.Post;
import org.sopt.domain.QPost;
import org.sopt.domain.QUser;
import org.sopt.post.domain.PostTag;

import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Post> searchPost(String title, String username, PostTag tag) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        return queryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(
                        titleContains(title),
                        usernameContains(username),
                        tagEquals(tag)
                )
                .fetch();
    }

    private BooleanExpression titleContains(String title){
        return (title != null && title.isEmpty()) ? QPost.post.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression usernameContains(String username){
        return (username != null && username.isEmpty()) ? QPost.post.user.name.containsIgnoreCase(username) : null;
    }

    private BooleanExpression tagEquals(PostTag tag){
        return tag != null ? QPost.post.tag.eq(tag) : null;
    }
}
