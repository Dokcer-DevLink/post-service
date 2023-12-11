package com.goorm.devlink.postservice.service.impl;


import com.goorm.devlink.postservice.entity.PostEntity;
import com.goorm.devlink.postservice.entity.StackEntity;
import com.goorm.devlink.postservice.repository.PostRepository;
import com.goorm.devlink.postservice.repository.StackRepository;
import com.goorm.devlink.postservice.service.PostService;
import com.goorm.devlink.postservice.util.ModelMapperUtil;
import com.goorm.devlink.postservice.vo.PostDetailRequest;
import com.goorm.devlink.postservice.vo.PostSimpleResponse;
import com.goorm.devlink.postservice.vo.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final StackRepository stackRepository;
    private final ModelMapperUtil modelMapperUtil;

    @Override
    public String createPost(PostDetailRequest postDetailRequest) {
        PostEntity postEntity = modelMapperUtil.convertToPostEntity(postDetailRequest);
        List<StackEntity> stackList = modelMapperUtil.convertToStackEntityList(postDetailRequest.getStacks(),postEntity);

        postRepository.save(postEntity);
        stackList.forEach( stackEntity -> stackRepository.save(stackEntity) );
        return postEntity.getPostUuid();
    }

    @Override
    public List<PostSimpleResponse> getPostList(PostType postType, String keyword) {
        List<PostEntity> findPostList = postRepository.findPostListByPostTypeAndKeyWord(postType,keyword);
        return modelMapperUtil.convertToPostSimpleResponseList(findPostList);
    }
}
