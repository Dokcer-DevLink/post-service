package com.goorm.devlink.postservice.querydsl;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.entity.StackEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.repository.StackRepository;
import com.goorm.devlink.postservice.vo.PostStatus;
import com.goorm.devlink.postservice.vo.PostType;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private StackRepository stackRepository;

    JPAQueryFactory jpaQueryFactory;


    @BeforeEach
    public void init(){
        jpaQueryFactory = new JPAQueryFactory(em);

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
            PostEntity postEntity = PostEntity.builder()
                    .postTitle("Tile"+i)
                    .postContent(("Content"+i))
                    .userUuid(userUuids.get((i/3)))
                    .postUuid(UUID.randomUUID().toString())
                    .postType(PostType.MENTOR)
                    .postStatus(PostStatus.WAITING)
                    .postImageUrl("default_image_url")
                    .address("address"+i)
                    .runningTime(30)
                    .isDeleted(false)
                    .build();

            StackEntity stackEntity = StackEntity.builder()
                            .stackName(stackNameList.get(i/3))
                            .post(postEntity)
                            .build();

            postRepository.save(postEntity);
            stackRepository.save(stackEntity);

        }

        for(int i =10;i<20;i++){
            PostEntity postEntity = PostEntity.builder()
                    .postTitle("Tile"+i)
                    .postContent(("Content"+i))
                    .userUuid(userUuids.get((i/3)))
                    .postUuid(UUID.randomUUID().toString())
                    .postType(PostType.MENTEE)
                    .postStatus(PostStatus.WAITING)
                    .postImageUrl("default_image_url")
                    .address("address"+i)
                    .runningTime(30)
                    .isDeleted(false)
                    .build();
            StackEntity stackEntity = StackEntity.builder()
                    .stackName(stackNameList.get(i/3))
                    .post(postEntity)
                    .build();
            postRepository.save(postEntity);
            stackRepository.save(stackEntity);
        }
    }





}
