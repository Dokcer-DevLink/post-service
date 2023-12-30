package com.goorm.devlink.postservice.repository.impl;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepositoryCustom;
import com.goorm.devlink.postservice.vo.PostType;
import com.goorm.devlink.postservice.vo.request.PostMatchingRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.goorm.devlink.postservice.entity.QPostEntity.postEntity;

public class PostRepositoryImpl implements PostRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager entityManager){
        queryFactory = new JPAQueryFactory(entityManager);
    }
    @Override // 무한스크롤 ( Slice )
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

    @Override
    public List<PostEntity> findPostMatchingByStackAndAddress(PostMatchingRequest postMatchingRequest) {

        return queryFactory.selectFrom(postEntity)
                .where(
                        postEntity.postType.eq(postMatchingRequest.getPostType()),
                        postEntity.onOffline.eq(postMatchingRequest.getOnOffline()),
                        searchStackCondition(postMatchingRequest.getStacks())
                )
                .orderBy(orderByCondition(postMatchingRequest.getAddressX(),postMatchingRequest.getAddressY()))
                .fetch();
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

    private OrderSpecifier orderByCondition(Double addressX, Double addressY){
        return ( addressX == null || addressY == null )?
                postEntity.createdDate.desc() : distanceCondition(addressX,addressY).asc();
    }


    private NumberExpression<Double> distanceCondition(Double addressX, Double addressY) {

        NumberExpression<Double> radiansX =
                Expressions.numberTemplate(Double.class, "radians({0})", addressX);

        NumberExpression<Double> cosX =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansX);
        NumberExpression<Double> cosPostX =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", postEntity.address.addressX);

        NumberExpression<Double> sinAddressX =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansX);
        NumberExpression<Double> sinPostAddressX =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))",  postEntity.address.addressX);

        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", postEntity.address.addressY, addressY);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosX.multiply(cosPostX).multiply(cosLongitude).add(sinAddressX.multiply(sinPostAddressX)));

        return Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

    }


}
