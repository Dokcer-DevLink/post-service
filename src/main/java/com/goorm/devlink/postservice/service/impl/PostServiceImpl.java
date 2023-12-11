package com.goorm.devlink.postservice.service.impl;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.ModelMapperUtil;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public List<PostSimpleResponse> getPostList(PostType postType, String keyword) {
        List<PostEntity> findPostList = postRepository.findPostListByPostTypeAndKeyWord(postType,keyword);
        return modelMapperUtil.convertToPostSimpleResponseList(findPostList);
    }
}
