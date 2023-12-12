package com.goorm.devlink.postservice.repository;

import com.goorm.devlink.postservice.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity,Long>, PostRepositoryCustom {

    PostEntity findByPostUuid(String postUuid);
    void deleteByPostUuid(String postUuid);
}
