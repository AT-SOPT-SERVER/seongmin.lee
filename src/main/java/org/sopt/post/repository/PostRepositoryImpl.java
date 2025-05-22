package org.sopt.post.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.sopt.post.domain.Post;
import org.sopt.post.domain.enums.PostTag;
import org.sopt.post.domain.QPost;
import org.sopt.user.domain.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Post> searchPost(Long userId, String title, String username, PostTag tag, Pageable pageable) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<Post> content =  queryFactory
                .selectFrom(post)
                .join(post.user, user).fetchJoin()
                .where(
                        titleContains(title),
                        usernameContains(username),
                        tagEquals(tag)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdTime.desc())
                .fetch();

        Long total = queryFactory
                .select(post.count())
                .from(post)
                .join(post.user, user)
                .where(
                        titleContains(title),
                        usernameContains(username),
                        tagEquals(tag)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, Optional.ofNullable(total).orElse(0L));
    }

    private BooleanExpression titleContains(String title){
        return (title != null && !title.isEmpty()) ? QPost.post.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression usernameContains(String username){
        return (username != null && !username.isEmpty()) ? QPost.post.user.name.containsIgnoreCase(username) : null;
    }

    private BooleanExpression tagEquals(PostTag tag){
        return tag != null ? QPost.post.tag.eq(tag) : null;
    }
}
