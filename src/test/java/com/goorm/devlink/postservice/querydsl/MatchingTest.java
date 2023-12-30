package com.goorm.devlink.postservice.querydsl;


import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.vo.PostType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.goorm.devlink.postservice.entity.QPostEntity.postEntity;

@SpringBootTest
public class MatchingTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;

    JPAQueryFactory queryFactory;

    @Test
    public void addressPriorityTest(){
        List<String> stacks = new ArrayList<>();
        stacks.add("Spring");
        stacks.add("JPA");

        double addressX = 127.92360027208;
        double addressY = 37.3829651508946;

        queryFactory = new JPAQueryFactory(em);

        List<String> result = queryFactory.select(postEntity.address.addressName)
                .from(postEntity)
                .where(
                        postEntity.postType.eq(PostType.MENTEE),
                        searchStackCondition(stacks)
                ).orderBy(distanceCondition(addressX, addressY).asc())
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }


    }

    public NumberExpression<Double> distanceCondition(Double addressX, Double addressY) {

         //latitude 를 radians 로 계산
        NumberExpression<Double> radiansX =
                Expressions.numberTemplate(Double.class, "radians({0})", addressX);

        // 계산된 latitude -> 코사인 계산
        NumberExpression<Double> cosX =
                Expressions.numberTemplate(Double.class, "cos({0})", radiansX);
        NumberExpression<Double> cosPostX =
                Expressions.numberTemplate(Double.class, "cos(radians({0}))", postEntity.address.addressX);

        // 계산된 latitude -> 사인 계산
        NumberExpression<Double> sinAddressX =
                Expressions.numberTemplate(Double.class, "sin({0})", radiansX);
        NumberExpression<Double> sinPostAddressX =
                Expressions.numberTemplate(Double.class, "sin(radians({0}))",  postEntity.address.addressX);

        // 사이 거리 계산
        NumberExpression<Double> cosLongitude =
                Expressions.numberTemplate(Double.class, "cos(radians({0}) - radians({1}))", postEntity.address.addressY, addressY);

        NumberExpression<Double> acosExpression =
                Expressions.numberTemplate(Double.class, "acos({0})", cosX.multiply(cosPostX).multiply(cosLongitude).add(sinAddressX.multiply(sinPostAddressX)));

        // 최종 계산
        return Expressions.numberTemplate(Double.class, "6371 * {0}", acosExpression);

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
