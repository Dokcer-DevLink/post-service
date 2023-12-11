package com.goorm.devlink.postservice.repository;

import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.vo.PostType;

import java.util.List;

public interface PostRepositoryCustom {

    List<PostEntity> findPostListByPostTypeAndKeyWord(PostType postType, String keyword);
}
