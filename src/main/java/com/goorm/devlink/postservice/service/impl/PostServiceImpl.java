package com.goorm.devlink.postservice.service.impl;


import com.goorm.devlink.postservice.dto.PostBasicDto;
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
    public String createPost(PostBasicDto postBasicDto) {
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(postBasicDto);
        postRepository.save(postEntity);
        return postEntity.getPostUuid();
    }

    @Override
    public List<PostSimpleResponse> getPostList(PostType postType, String keyword) {
        List<PostEntity> findPostList = postRepository.findPostListByPostTypeAndKeyWord(postType,keyword);
        return modelMapperUtil.convertToPostSimpleResponseList(findPostList);
    }

    @Override
    public void editPost(PostBasicDto instanceForEdit) {
        long postId = postRepository.findByPostUuid(instanceForEdit.getPostUuid()).getId();
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(instanceForEdit);
        postEntity.updateForMerge(postId);
        postRepository.save(postEntity);
    }
}
