package com.goorm.devlink.postservice.repository;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.vo.PostType;
import com.goorm.devlink.postservice.vo.request.PostMatchingRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    Page<PostEntity> findPostListByPostTypeAndKeyWord(PostType postType, String keyword, Pageable pageable);

    Page<PostEntity> findPostListByPostTypeAndStacks(PostType postType, List<String> profileStacks, Pageable pageable);

    List<PostEntity> findPostMatchingByStackAndAddress(PostMatchingRequest postMatchingRequest);

}
