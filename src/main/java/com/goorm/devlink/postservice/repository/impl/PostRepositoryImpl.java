package com.goorm.devlink.postservice.repository.impl;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepositoryCustom;
import com.goorm.devlink.postservice.vo.PostType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import java.util.List;

import static com.goorm.devlink.postservice.entity.QPostEntity.postEntity;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager){
        queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public Page<PostEntity> findPostListByPostTypeAndKeyWord(PostType postType, String keyword, Pageable pageable) {

        List<PostEntity> postList = queryFactory
                .selectFrom(postEntity)
                .where(
                        postEntity.postType.eq(postType),
                        searchKeywordCondition(keyword)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(
                        postEntity.postType.eq(postType),
                        searchKeywordCondition(keyword)
                ).fetchOne();

        return new PageImpl<>(postList,pageable,count);
    }

    @Override
    public Page<PostEntity> findPostListByPostTypeAndStacks(PostType postType, List<String> profileStacks, Pageable pageable) {
        List<PostEntity> postList = queryFactory.selectFrom(postEntity)
                .where(
                        postEntity.postType.eq(postType),
                        searchStackCondition(profileStacks)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(postEntity.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(postEntity.count())
                .from(postEntity)
                .where(
                        postEntity.postType.eq(postType),
                        searchStackCondition(profileStacks)
                ).fetchOne();

        return new PageImpl<>(postList,pageable,count);
    }

    private BooleanBuilder searchKeywordCondition(String keyword){
        return ( keyword == null || keyword.isEmpty() )? null : getKeywordConditionBuilder(keyword);
    }

    private BooleanBuilder getKeywordConditionBuilder(String keyword){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(postEntity.postTitle.contains(keyword));
        booleanBuilder.or(postEntity.stacks.contains(keyword));
        //booleanBuilder.or(postEntity.postContent.contains(keyword));
        return booleanBuilder;
    }

    private BooleanBuilder searchStackCondition(List<String> profileStacks){
        return ( profileStacks == null)? null : getStackConditionBuilder(profileStacks);
    }

    private BooleanBuilder getStackConditionBuilder(List<String> profileStacks) {
        BooleanBuilder booleanBUilder = new BooleanBuilder();
        for (String profileStack : profileStacks) {
            booleanBUilder.or(postEntity.postTitle.contains(profileStack));
            booleanBUilder.or(postEntity.stacks.contains(profileStack));
        }
        return booleanBUilder;
    }

}
