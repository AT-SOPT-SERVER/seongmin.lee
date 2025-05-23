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
    public Page<Post> searchPost(Long userId, String title, String username, List<PostTag> tags, Pageable pageable) {
        QPost post = QPost.post;
        QUser user = QUser.user;

        List<Post> content =  queryFactory
                .selectFrom(post)
                .distinct()
                .join(post.user, user).fetchJoin()
                .where(
                        userIdEq(userId),
                        titleContains(title),
                        usernameContains(username),
                        tagIn(tags)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdTime.desc())
                .fetch();

        Long total = queryFactory
                .select(post.id.countDistinct())
                .distinct()
                .from(post)
                .join(post.user, user)
                .where(
                        userIdEq(userId),
                        titleContains(title),
                        usernameContains(username),
                        tagIn(tags)
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, Optional.ofNullable(total).orElse(0L));
    }

    private BooleanExpression userIdEq(Long userId){
        return (userId != null) ? QPost.post.user.id.eq(userId) : null;
    }

    private BooleanExpression titleContains(String title){
        return (title != null && !title.isEmpty()) ? QPost.post.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression usernameContains(String username){
        return (username != null && !username.isEmpty()) ? QPost.post.user.name.containsIgnoreCase(username) : null;
    }

    private BooleanExpression tagIn(List<PostTag> tags){
        return (tags != null && tags.isEmpty()) ? QPost.post.tags.any().in(tags) : null;
    }
}
