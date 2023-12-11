package com.goorm.devlink.postservice.querydsl;


import com.goorm.devlink.postservice.repository.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    private PostRepository postRepository;

    JPAQueryFactory jpaQueryFactory;


    @BeforeEach
    public void init(){
        jpaQueryFactory = new JPAQueryFactory(em);


    }


}
