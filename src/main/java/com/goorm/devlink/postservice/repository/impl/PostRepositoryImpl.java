package com.goorm.devlink.postservice.repository.impl;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepositoryCustom;
import com.goorm.devlink.postservice.vo.PostType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.goorm.devlink.postservice.entity.QPostEntity.postEntity;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager){
        queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public List<PostEntity> findPostListByPostTypeAndKeyWord(PostType postType, String keyword) {

        return queryFactory
                .selectFrom(postEntity)
                .where(
                        postEntity.postType.eq(postType),
                        searchKeywordCondition(keyword)
                ).fetch();
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

}
