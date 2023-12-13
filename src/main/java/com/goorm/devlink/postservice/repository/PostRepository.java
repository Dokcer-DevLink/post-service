package com.goorm.devlink.postservice.repository;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.vo.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long>, PostRepositoryCustom {

    PostEntity findByPostUuid(String postUuid);
    Page<PostEntity> findByUserUuidAndPostType(String userUuid, PostType postType, Pageable pageable);
}
