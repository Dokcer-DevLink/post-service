package com.goorm.devlink.postservice.querydsl;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.vo.PostType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.goorm.devlink.postservice.entity.QPostEntity.postEntity;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private PostRepository postRepository;

    JPAQueryFactory queryFactory;


    @BeforeEach
    public void init(){
        queryFactory = new JPAQueryFactory(em);


        List<String> userUuids = new ArrayList<>();
        userUuids.add("11111111");
        userUuids.add("22222222");
        userUuids.add("33333333");

        List<String> stackNameList  = new ArrayList<>();
        stackNameList.add("Spring");
        stackNameList.add("NodeJS");
        stackNameList.add("React");

        List<PostEntity> postList = new ArrayList<>();

        for(int i =0;i<10;i++){
            PostEntity postEntity = PostEntity.getInstanceTest(i, PostType.MENTOR, userUuids.get((i % 3)),stackNameList);

            postRepository.save(postEntity);
        }

        for(int i =10;i<20;i++){
            PostEntity postEntity = PostEntity.getInstanceTest(i, PostType.MENTEE, userUuids.get((i % 3)),stackNameList);
            postRepository.save(postEntity);
        }

        em.flush();
        em.clear();
    }

    @Test
    public void findPostListByPostTypeAndKeyWordTest(){

        PostType postType = PostType.MENTOR;
        String keyword = "Spring"   ; // 키워드는 있을수도 없을수도 있음
        List<PostEntity> postList = queryFactory
                                        .selectFrom(postEntity)
                                        .where(
                                                postEntity.postType.eq(postType),
                                                searchKeywordCondition(keyword)
                                        ).fetch();

        System.out.println(postList.size());
        for (PostEntity postEntity : postList) {
            System.out.println(postEntity.getPostTitle());
            System.out.println(postEntity.getPostContent());
            System.out.println(postEntity.getStacks().size());
            postEntity.getStacks().forEach(stack ->{
                System.out.println(stack);
            });
            System.out.println();
        }
    }

    private BooleanBuilder searchKeywordCondition(String keyword){
        return ( keyword == null || keyword.isEmpty() )? null : getKeywordConditionBuilder(keyword);
    }

    private BooleanBuilder getKeywordConditionBuilder(String keyword){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(postEntity.postTitle.contains(keyword));
        booleanBuilder.or(postEntity.postContent.contains(keyword));
        booleanBuilder.or(postEntity.stacks.contains(keyword));

        return booleanBuilder;
    }

}
